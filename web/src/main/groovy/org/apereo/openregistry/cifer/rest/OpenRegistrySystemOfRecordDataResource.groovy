package org.apereo.openregistry.cifer.rest

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

/**
 * A RESTful resource implementation to handle https://spaces.internet2.edu/display/cifer/SOR-Registry+Strawman+Write+API
 */
@RestController
@RequestMapping("/v1")
class OpenRegistrySystemOfRecordDataResource {

    @Value("\${openregistry.version:2.0-M1-SNAPSHOT}")
    private orVersion

    @RequestMapping(method = RequestMethod.PUT, value = "/sorPeople/{sor}/{sorId}")
    @ResponseBody
    String createOrUpdatePerson(@PathVariable("sor") String sor, @PathVariable("sorId") String personSorId) {
        //The presence of @ResponseBody and Jackson Message Converter that Boot adds to the classpath
        //will cause this Map to serialized as a JSON blob on the client side!
        [openRegistryVersion: this.orVersion,
                personCreated: [sor: sor, sorPerson: personSorId]]
    }
}
