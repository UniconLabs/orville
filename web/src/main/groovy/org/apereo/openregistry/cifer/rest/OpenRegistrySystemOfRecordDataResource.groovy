package org.apereo.openregistry.cifer.rest

import groovy.util.logging.Slf4j
import org.apereo.openregistry.model.Baggage
import org.apereo.openregistry.model.SystemOfRecord
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
}
