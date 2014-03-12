package org.apereo.openregistry.cifer.rest

import groovy.util.logging.Slf4j
import org.apereo.openregistry.service.OpenRegistryProcessor
import org.apereo.openregistry.service.OpenRegistryProcessorContext
import org.apereo.openregistry.service.SystemOfRecordPersonFactory
import org.apereo.openregistry.service.SystemOfRecordPersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

/**
 * A RESTful resource implementation to handle https://spaces.internet2.edu/display/cifer/SOR-Registry+Strawman+Write+API
 */
@RestController
@RequestMapping("/v1")
@Slf4j
class OpenRegistrySystemOfRecordDataResource {

    @Autowired
    private SystemOfRecordPersonFactory systemOfRecordPersonFactory

    //TODO: Currently a noop mock impl is wired in. Need a real impl
    @Autowired
    private SystemOfRecordPersonRepository systemOfRecordPersonRepository

    //TODO: Not wired in at the moment. Need an impl. and @Bean definition method in Application class
    private OpenRegistryProcessor openRegistryProcessor

    @RequestMapping(method = RequestMethod.PUT, value = "/sorPeople/{sor}/{sorId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    def updateSorPerson(@RequestBody Map<String, Object> sorData, @PathVariable("sor") String sor, @PathVariable("sorId") String personSorId) {
        //TODO: figure out Boot's logback config for app level logging levels
        log.debug("Calling PUT /sorPeople/* ...")

        def sorPerson = this.systemOfRecordPersonRepository.findBySystemOfRecordAndSystemOfRecordId(sor, personSorId)
        if(!sorPerson) {
            def msg = "An SOR person is not found for the following SOR ID [$personSorId] and System Of Record [$sor]"
            log.warn(msg)
            return new ResponseEntity<String>(msg.toString(), HttpStatus.NOT_FOUND)
        }

        this.openRegistryProcessor.process(new OpenRegistryProcessorContext(request: sorData, systemOfRecordPerson: sorPerson))

        //According to "specs" or requirement docs, there is no specific response body on HTTP 200. So not returning anything here
    }
}
