package org.apereo.openregistry.service.identification

import groovy.util.logging.Slf4j
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
        //TODO implement....
        return processorContext
    }
}
