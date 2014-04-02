package org.apereo.openregistry.service.identification

import groovy.util.logging.Slf4j
import org.apereo.openregistry.model.Identifier
import org.apereo.openregistry.model.request.OpenRegistryProcessingRequest
import org.apereo.openregistry.service.OpenRegistryProcessor
import org.apereo.openregistry.service.OpenRegistryProcessorContext
import static org.apereo.openregistry.model.request.OpenRegistryProcessingRequest.TYPE

/**
 *
 * A processor implementation for identification phase - to generate identifiers for system of record (sor) people
 */
@Slf4j
class SystemOfRecordIdentificationProcessor implements OpenRegistryProcessor {

    @Override
    OpenRegistryProcessorContext process(OpenRegistryProcessorContext processorContext) {
        log.info("Starting 'sor identification' processing phase with [$processorContext]")
        //TODO: currently this is a mock impl. Add the real impl...

        //Only assign new identifier if it's an 'add' (POST) request (no sor id given) and no identifiers already assigned
        //Perhaps by the previous identifier processors in the pipeline???
        if (processorContext.request.type == TYPE.add
                && doesntHaveSorIds(processorContext.openRegistryPerson.identifiers)) {
            def mockIdentifier = new Identifier(identifierType: Identifier.IdentifierType.sor, value: 'GUEST000797031')
            log.info("Generated '$mockIdentifier'")
            processorContext.openRegistryPerson.identifiers << mockIdentifier
        }
        return processorContext
    }

    private doesntHaveSorIds(Set<Identifier> identifiers) {
        identifiers.findAll { it.identifierType == Identifier.IdentifierType.sor }?.size() == 0
    }
}
