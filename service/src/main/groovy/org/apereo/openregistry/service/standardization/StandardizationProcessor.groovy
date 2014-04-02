package org.apereo.openregistry.service.standardization

import groovy.util.logging.Slf4j
import org.apereo.openregistry.service.OpenRegistryProcessor
import org.apereo.openregistry.service.OpenRegistryProcessorContext

/**
 *
 * A processor implementation for standardization phase
 */
@Slf4j
class StandardizationProcessor implements OpenRegistryProcessor {

    @Override
    OpenRegistryProcessorContext process(OpenRegistryProcessorContext processorContext) {
        log.info("Starting 'standardization' processing phase with [$processorContext]")
        //TODO implement....
        return processorContext
    }
}
