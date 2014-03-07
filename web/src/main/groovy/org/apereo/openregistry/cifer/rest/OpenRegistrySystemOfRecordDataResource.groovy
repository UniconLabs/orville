package org.apereo.openregistry.cifer.rest

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

    @RequestMapping(method = RequestMethod.PUT, value = "/sorPeople/{sor}/{sorId}")
    String createOrUpdatePerson(@PathVariable("sor") String sor, @PathVariable("sorId") String personSorId) {
        "sor: ${sor}\nperson: ${personSorId}\n"
    }
}
