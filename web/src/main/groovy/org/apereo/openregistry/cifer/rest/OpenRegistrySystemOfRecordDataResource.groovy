package org.apereo.openregistry.cifer.rest

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

/**
 * A RESTful resource implementation to handle https://spaces.internet2.edu/display/cifer/SOR-Registry+Strawman+Write+API
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/v1")
class OpenRegistrySystemOfRecordDataResource {

    @RequestMapping(method = RequestMethod.PUT, value = "/sorPeople/{sor}/{sorId}")
    @ResponseBody
    String createOrUpdatePerson(@PathVariable("sor") String sor, @PathVariable("sorId") String personSorId) {
        return "sor: ${sor}\nperson: ${personSorId}"
    }
}
