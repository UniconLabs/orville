package org.apereo.openregistry.cifer.rest

import groovy.json.JsonBuilder
import groovy.util.logging.Slf4j
import org.apereo.openregistry.model.Baggage
import org.apereo.openregistry.model.EmailAddressIdentifier
import org.apereo.openregistry.model.Identifier
import org.apereo.openregistry.model.Person
import org.apereo.openregistry.model.Role
import org.apereo.openregistry.model.SystemOfRecord
import org.apereo.openregistry.model.TokenIdentifier
import org.apereo.openregistry.model.Type
import org.apereo.openregistry.service.OpenRegistryProcessor
import org.apereo.openregistry.service.OpenRegistryProcessorContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import javax.xml.bind.DatatypeConverter

/**
 * A RESTful resource implementation to handle https://spaces.internet2.edu/display/cifer/SOR-Registry+Strawman+Write+API
 */
@RestController
@RequestMapping("/v1")
@Slf4j
class OpenRegistrySystemOfRecordDataResource {

    @Autowired
    @Qualifier("defaultOpenRegistryProcessingEngine")
    private OpenRegistryProcessor openRegistryProcessor

    @Autowired
    @Qualifier("updateProcessor")
    OpenRegistryProcessor updateProcessor

    @RequestMapping(method = RequestMethod.GET, value = "/people", produces = MediaType.APPLICATION_JSON_VALUE)
    def getPeople(@RequestParam(value = "sponsor", required = false) String sponsor, @RequestParam(value = "validThrough", required = false) String validThrough) {
        if (!sponsor && !validThrough) {
            return new ResponseEntity([people: Person.all.collect { "/v1/people/${it.id}".toString() }], HttpStatus.OK)
        } else {
            try {
                DatatypeConverter
                Person.withTransaction {
                    // TODO: this is going to get real ugly real quick. check for validThrough and sponsor and do queries based on what we have. This will need to be fixed later
                    def roles = [] as List<Role>
                    def sponsors

                    def actions =[]

                    if (validThrough) {
                        def exActions = [
                                'lt': 'ExpirationLessThan',
                                'gt': 'ExpirationGreaterThan'
                        ]
                        def (action, dateString) = validThrough.split("\\.", 2)
                        def date = Date.parse("yyyy-MM-dd", dateString)
                        assert action in exActions.keySet()
                        actions << [exActions[action], date]
                    }

                    if (sponsor) {
                        def (type, id) = sponsor.split(":", 2)
                        sponsors = TokenIdentifier.findAllByTypeAndToken(Type.findByTargetAndValue(TokenIdentifier, type), id) as List<TokenIdentifier>
                        actions << ['SponsorInList', sponsors]
                    }
                    roles = Role."findBy${actions.collect {it[0]}.join('And')}"(*actions.collect {it[1]}.toArray())

                    return new ResponseEntity([people: roles.collect {"/v1/people/${it.person.id}".toString()}], HttpStatus.OK)
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST)
            }
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/people/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    def getPerson(@PathVariable("id") String id) {
        Person.withTransaction {
            def person = Person.get(id)
            if (!person) {
                return new ResponseEntity(HttpStatus.NOT_FOUND)
            } else {
                return new ResponseEntity(
                        [
                                id: person.id,
                                names: person.nameIdentifiers.collect {
                                    [
                                            id    : it.id,
                                            type  : it.type.value,
                                            given : it.name.givenName,
                                            family: it.name.familyName
                                    ]
                                },
                                identifiers: person.tokenIdentifiers.collect {
                                    [
                                            id: it.id,
                                            type: it.type.value,
                                            identifier: it.token
                                    ]
                                },
                                roles: Role.findByPerson(person).collect {
                                    [
                                            id: it.id,
                                            sponsor: [
                                                    type: it.sponsor.type.value,
                                                    identifier: it.sponsor.token
                                            ],
                                            expiration: DatatypeConverter.printDateTime(it.expiration.toCalendar())
                                    ]
                                },
                                emailAddresses: person.wallet.findAll {EmailAddressIdentifier.isAssignableFrom(it.class)}.collect {
                                    [
                                            id: it.id,
                                            type: it.type.value,
                                            address: it.emailAddress
                                    ]
                                },
                                baggage: person.baggage.collect {
                                    [
                                            id: it.id,
                                            type: it.type.value,
                                            dateCreated: DatatypeConverter.printDateTime(it.dateCreated.toCalendar()),
                                            contents: it.contents
                                    ]
                                }
                        ],
                        HttpStatus.OK)
            }
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/sorPeople/{sor}/{sorId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    def updateSorPerson(
            @RequestBody Map<String, Object> sorRequestData,
            @PathVariable("sor") String sor, @PathVariable("sorId") String personSorId) {
        Person.withTransaction {
            def systemOfRecord = SystemOfRecord.findByActiveAndCode(true, sor)
            if (!systemOfRecord) {
                return new ResponseEntity(HttpStatus.NOT_FOUND)
            }
            def person = TokenIdentifier.findByTypeAndSystemOfRecordAndToken(
                    Type.findByTargetAndValue(TokenIdentifier, "guest-sor"),
                    SystemOfRecord.findByCodeAndActive(sor, true),
                    personSorId
            )?.person
            if (!person) {
                return new ResponseEntity(HttpStatus.NOT_FOUND)
            }
            sorRequestData["resource"] = "/v1/sorPeople/${sor}/${personSorId}"
            updateProcessor.process(new OpenRegistryProcessorContext(
                    person: person,
                    request: new Baggage(
                            systemOfRecord: systemOfRecord,
                            contents: sorRequestData,
                            type: Type.findByTargetAndValue(Baggage, "update")
                    )
            ))
            return new ResponseEntity(HttpStatus.OK)
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/sorPeople/{sor}", consumes = MediaType.APPLICATION_JSON_VALUE)
    def createSorPerson(@RequestBody Map<String, Object> sorRequestData, @PathVariable("sor") String sor) {
        def systemOfRecord = SystemOfRecord.findByActiveAndCode(true, sor)
        if (!systemOfRecord) {
            return new ResponseEntity(HttpStatus.NOT_FOUND)
        }
        OpenRegistryProcessorContext processorCtx = this.openRegistryProcessor.process(
                new OpenRegistryProcessorContext(request:
                        new Baggage(systemOfRecord: systemOfRecord, contents: sorRequestData, type: Type.findByTargetAndValue(Baggage, "add"))))

        switch (processorCtx.outcome?.idMatchType) {
            case 'OK':
                return new ResponseEntity(HttpStatus.OK)
            case 'CREATED':
                //outcome.body is a Map
                return new ResponseEntity(processorCtx.outcome.body, HttpStatus.CREATED)
            case 'MULTIPLE_CHOICES':
                //outcome.body is a Map
                return new ResponseEntity(processorCtx.outcome.body, HttpStatus.MULTIPLE_CHOICES)
            default:
                //TODO: what do we do here? Is 500 OK?
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)

        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/sorPeople/{sor}/{sorId}/{roleId}")
    def deleteRole(@PathVariable("sor") String sor, @PathVariable("sorId") String sorId, @PathVariable("roleId") int roleId) {
        Role.withTransaction {
            def systemOfRecord = SystemOfRecord.findByCodeAndActive(sor, true)
            if (!systemOfRecord) {
                return new ResponseEntity(HttpStatus.NOT_FOUND)
            }
            def person = TokenIdentifier.findBySystemOfRecordAndTokenAndType(systemOfRecord, sorId, Type.findByTargetAndValue(TokenIdentifier, "guest-sor"))?.person
            if (!person) {
                return new ResponseEntity(HttpStatus.NOT_FOUND)
            }
            def role = Role.findByIdAndPersonAndSystemOfRecord(roleId, person, systemOfRecord)
            if (!role) {
                return new ResponseEntity(HttpStatus.NOT_FOUND)
            }
            // TODO: better handling of this elsewhere
            def s = [
                    type: role.type.value,
                    id: role.id,
                    systemOfRecord: role.systemOfRecord.code,
                    sponsor: [
                            type: role.sponsor.type.value,
                            identifier: role.sponsor.token,
                            systemOfRecord: role.sponsor.systemOfRecord.code
                    ],
                    expiration: role.expiration
            ]
            role.person.addToBaggage(
                    systemOfRecord: role.systemOfRecord,
                    contents: s,
                    type: Type.findByTargetAndValue(Baggage, "delete"))
            person.removeFromWallet(role)
            person.save(failOnError: true)
            role.delete()

            return new ResponseEntity(HttpStatus.NO_CONTENT)
        }
    }
}
