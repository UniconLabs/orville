package org.apereo.openregistry.cifer.rest

import groovy.util.logging.Slf4j
import org.apereo.openregistry.service.activation.ActivationKeyService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

/**
 *
 * RESTful resource implementing CIFER activation keys
 */
@RestController
@RequestMapping("/v1")
@Slf4j
class ActivationKeysResource {

    //TODO create impl and inject
    ActivationKeyService activationKeyService

    @RequestMapping(method = RequestMethod.POST, value = "/activationKeys", consumes = MediaType.APPLICATION_JSON_VALUE)
    def createActivationKey(@RequestBody Map<String, Object> activationKeyRequestData) {
        log.info("Received request for the new activation key with the following payload: ${activationKeyRequestData.toString()}")

        //TODO error handling

        def ak = this.activationKeyService.createFor(activationKeyRequestData.identifier.identifier, activationKeyRequestData.identifier.type)
        new ResponseEntity([activationKey: ak.value, validThrough: ak.validThroughAsString], HttpStatus.OK)

    }
}
