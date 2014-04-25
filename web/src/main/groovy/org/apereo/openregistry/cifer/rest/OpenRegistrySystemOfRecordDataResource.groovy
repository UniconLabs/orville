package org.apereo.openregistry.cifer.rest

import groovy.json.JsonBuilder
import groovy.util.logging.Slf4j
import org.apereo.openregistry.model.Baggage
import org.apereo.openregistry.model.Person
import org.apereo.openregistry.model.Role
import org.apereo.openregistry.model.SystemOfRecord
import org.apereo.openregistry.model.TokenIdentifier
import org.apereo.openregistry.model.Type
import org.apereo.openregistry.service.OpenRegistryProcessor
import org.apereo.openregistry.service.OpenRegistryProcessorContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * A RESTful resource implementation to handle https://spaces.internet2.edu/display/cifer/SOR-Registry+Strawman+Write+API
 */
@RestController
@RequestMapping("/v1")
@Slf4j
class OpenRegistrySystemOfRecordDataResource {

    @Autowired
    private OpenRegistryProcessor openRegistryProcessor

    @RequestMapping(method = RequestMethod.PUT, value = "/sorPeople/{sor}/{sorId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED)
    def updateSorPerson(
            @RequestBody Map<String, Object> sorRequestData,
            @PathVariable("sor") String sor, @PathVariable("sorId") String personSorId) {

        //TODO: implement when mode business requirements details are available. HTTP 501 (not implemented) for now...
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
