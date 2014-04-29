package org.apereo.openregistry.cifer.rest

import groovy.util.logging.Slf4j
import org.apereo.openregistry.service.activation.ActivationKeyNotFoundException
import org.apereo.openregistry.service.activation.ActivationKeyService
import org.apereo.openregistry.service.activation.IdentifierNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

/**
 *
 * RESTful resource implementing CIFER activation keys
 */
@RestController
@RequestMapping("/v1")
@Slf4j
class ActivationKeysResource {

    @Autowired
    ActivationKeyService activationKeyService

    @RequestMapping(method = RequestMethod.POST, value = "/activationKeys", consumes = MediaType.APPLICATION_JSON_VALUE)
    def createActivationKey(@RequestBody Map<String, Object> activationKeyRequestData) {
        log.info("Received request for the new activation key with the following payload: $activationKeyRequestData")

        def activationKey = this.activationKeyService.createAndPersist(activationKeyRequestData)
        new ResponseEntity([activationKey: activationKey.id,
                            validThrough: activationKey.validThroughDateAsUtcFormattedString], HttpStatus.OK)

    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/activationKeys/{activationKey}")
    def validateActivationKey(@PathVariable("activationKey") String activationKeyValue) {
        this.activationKeyService.validate(activationKeyValue)
        new ResponseEntity(HttpStatus.OK)
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    def handleIdentifierNotFoundException(IdentifierNotFoundException ex) {
        ex.message
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    def handleActivationKeyNotFoundException(ActivationKeyNotFoundException ex) {
        ex.message
    }
}
