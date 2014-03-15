package org.apereo.openregistry.cifer.rest

import groovy.util.logging.Slf4j
import org.apereo.openregistry.model.request.OpenRegistryProcessingRequest
import org.apereo.openregistry.service.standardization.SystemOfRecordPersonFactory
import org.apereo.openregistry.service.OpenRegistryProcessor
import org.apereo.openregistry.service.OpenRegistryProcessorContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

import static org.apereo.openregistry.model.request.OpenRegistryProcessingRequest.TYPE

/**
 * A RESTful resource implementation to handle https://spaces.internet2.edu/display/cifer/SOR-Registry+Strawman+Write+API
 */
@RestController
@RequestMapping("/v1")
@Slf4j
class OpenRegistrySystemOfRecordDataResource {

    @Autowired
    private SystemOfRecordPersonFactory systemOfRecordPersonFactory

    //TODO: Not wired in at the moment. Need an impl. and @Bean definition method in Application class
    private OpenRegistryProcessor openRegistryProcessor

    @RequestMapping(method = RequestMethod.PUT, value = "/sorPeople/{sor}/{sorId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    def updateSorPerson(
            @RequestBody Map<String, Object> sorRequestData,
            @PathVariable("sor") String sor, @PathVariable("sorId") String personSorId) {

        //TODO: figure out Boot's logback config for app level logging levels
        log.debug("Calling PUT /sorPeople/* ...")

        OpenRegistryProcessorContext processorContext = this.openRegistryProcessor.process(
                new OpenRegistryProcessorContext(request:
                        new OpenRegistryProcessingRequest(sor: sor, sorPersonId: personSorId, body: sorRequestData, type: TYPE.update)))

        if (processorContext.outcome.personNotFound) {
            def msg = "An SOR person is not found for the following SOR ID [$personSorId] and System Of Record [$sor]"
            log.warn(msg)
            return new ResponseEntity(msg.toString(), HttpStatus.NOT_FOUND)
        }
        //According to "specs" or requirement docs, there is no specific response body on HTTP 200. So not returning anything here
    }

    @RequestMapping(method = RequestMethod.POST, value = "/sorPeople/{sor}", consumes = MediaType.APPLICATION_JSON_VALUE)
    def createSorPerson(@RequestBody Map<String, Object> sorRequestData, @PathVariable("sor") String sor) {

        OpenRegistryProcessorContext processorCtx = this.openRegistryProcessor.process(
                new OpenRegistryProcessorContext(request:
                        new OpenRegistryProcessingRequest(sor: sor, body: sorRequestData, type: TYPE.add)))

        switch (processorCtx.outcome.idMatchType) {
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
}
