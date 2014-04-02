package org.apereo.openregistry.service.identification

import groovy.util.logging.Slf4j
import org.apereo.openregistry.service.OpenRegistryProcessor
import org.apereo.openregistry.service.OpenRegistryProcessorContext

/**
 *
 * A processor implementation for identification phase
 */
@Slf4j
class IdentificationProcessor implements OpenRegistryProcessor {

    @Override
    OpenRegistryProcessorContext process(OpenRegistryProcessorContext processorContext) {
        log.info("Starting 'identification' processing phase with [$processorContext]")
        //TODO implement....
        return processorContext
    }
}
