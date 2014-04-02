package org.apereo.openregistry.service.election

import groovy.util.logging.Slf4j
import org.apereo.openregistry.service.OpenRegistryProcessor
import org.apereo.openregistry.service.OpenRegistryProcessorContext

/**
 *
 * A processor implementation for election phase
 */
@Slf4j
class ElectionProcessor implements OpenRegistryProcessor {

    @Override
    OpenRegistryProcessorContext process(OpenRegistryProcessorContext processorContext) {
        log.info("Starting 'election' processing phase with [$processorContext]")
        //TODO implement....
        return processorContext
    }
}
