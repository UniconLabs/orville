package org.apereo.openregistry.service.identification

import groovy.transform.EqualsAndHashCode
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
@EqualsAndHashCode
class IdentificationProcessor implements OpenRegistryProcessor {

    @Override
    OpenRegistryProcessorContext process(OpenRegistryProcessorContext processorContext) {
        log.info("Starting 'identification' processing phase with [$processorContext]")
        //TODO real implementation.

        return processorContext
    }
}
