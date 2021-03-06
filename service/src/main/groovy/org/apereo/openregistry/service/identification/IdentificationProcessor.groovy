package org.apereo.openregistry.service.identification

import groovy.transform.EqualsAndHashCode
import groovy.util.logging.Slf4j
import org.apereo.openregistry.service.OpenRegistryProcessor
import org.apereo.openregistry.service.OpenRegistryProcessorContext

/**
 *
 * A processor implementation for identification phase - to generate identifiers for open registry (calculated) people
 */
@Slf4j
@EqualsAndHashCode
class IdentificationProcessor implements OpenRegistryProcessor {

    final TokenIdentifierService tokenIdentifierService

    final String identifierType

    IdentificationProcessor(TokenIdentifierService tokenIdentifierService, String identifierType) {
        this.tokenIdentifierService = tokenIdentifierService
        this.identifierType = identifierType
    }

    @Override
    OpenRegistryProcessorContext process(OpenRegistryProcessorContext processorContext) {
        log.info("Starting 'identification' processing phase with [$processorContext]")
        def sorId = this.tokenIdentifierService.createFor(processorContext.request.systemOfRecord, processorContext.person, identifierType)
        processorContext.person.addToWallet sorId

        //TODO: this might be moved elsewhere
        if (!processorContext.outcome.body) {
            processorContext.outcome.body = [:]
        }
        if (!processorContext.outcome.body.identifiers) {
            processorContext.outcome.body.identifiers = []
        }
        processorContext.outcome.body.identifiers << [identifier: sorId.token, type: "sor"]

        return processorContext
    }
}
