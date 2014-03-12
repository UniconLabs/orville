package org.apereo.openregistry.cifer.rest

import groovy.util.logging.Slf4j
import org.apereo.openregistry.service.SystemOfRecordPersonFactory
import org.apereo.openregistry.service.SystemOfRecordPersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

/**
 * A RESTful resource implementation to handle https://spaces.internet2.edu/display/cifer/SOR-Registry+Strawman+Write+API
 */
@RestController
@RequestMapping("/v1")
@Slf4j
class OpenRegistrySystemOfRecordDataResource {

    //Injected from openregistry.properties or if not present uses the default value defined inline (after the (:) colon token)
    //This is here just to test Boot's capabilities in the initial stages of development. Will be removed later...
    @Value("\${openregistry.version:2.0-M1-SNAPSHOT}")
    private orVersion

    @Autowired
    private SystemOfRecordPersonFactory systemOfRecordPersonFactory

    private SystemOfRecordPersonRepository systemOfRecordPersonRepository



    @RequestMapping(method = RequestMethod.PUT, value = "/sorPeople/{sor}/{sorId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    def updateSorPerson(@RequestBody Map<String, Object> sorData, @PathVariable("sor") String sor, @PathVariable("sorId") String personSorId) {
        //TODO: figure out Boot's logback config for app level logging levels
        log.debug("Calling PUT /sorPeople/* ...")
        log.info("Spring Boot has injected [${this.systemOfRecordPersonFactory}")
        log.info("The submitted SOR data JSON blob: $sorData")
        //The presence of @RestController as a type-level annotation and Jackson Message Converter that Boot adds to the classpath
        //will cause this Map to serialized as a JSON blob (application/json Content-Type) on the client side!
        [openRegistryVersion: this.orVersion,
                personCreated: [sor: sor, sorPerson: personSorId]]
    }
}
