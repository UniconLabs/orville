package org.apereo.openregistry.service.identification

import groovy.util.logging.Slf4j
import org.apereo.openregistry.model.Identifier
import org.apereo.openregistry.model.request.OpenRegistryProcessingRequest
import org.apereo.openregistry.service.OpenRegistryProcessor
import org.apereo.openregistry.service.OpenRegistryProcessorContext

/**
 *
 * A processor implementation for identification phase - to generate identifiers for open registry (calculated) people
 */
@Slf4j
class OpenRegistryIdentificationProcessor implements OpenRegistryProcessor {

    @Override
    OpenRegistryProcessorContext process(OpenRegistryProcessorContext processorContext) {
        log.info("Starting 'open registry identification' processing phase with [$processorContext]")
        //TODO real implementation.

        //Only assign new identifier if no identifiers already assigned
        //Perhaps by the previous identifier processors in the pipeline???
        if (doesntHaveEnterpriseIds(processorContext.openRegistryPerson.identifiers)) {
            def mockIdentifier = new Identifier(identifierType: Identifier.IdentifierType.enterprise, value: '103459')
            log.info("Generated '$mockIdentifier'")
            processorContext.openRegistryPerson.identifiers << mockIdentifier
        }
        return processorContext
    }

    private doesntHaveEnterpriseIds(Set<Identifier> identifiers) {
        identifiers.findAll { it.identifierType == Identifier.IdentifierType.enterprise }?.size() == 0
    }
}
