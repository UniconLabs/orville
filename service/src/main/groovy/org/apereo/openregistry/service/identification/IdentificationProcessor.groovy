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
        //At this stage the sor person AND open registry person should be available in the ctx, right????
        //The 'outcome' field (Object) in the ctx could be set to any non-null value to signal to a composite processor
        //mediating the overall processing pipeline, a stop in further processing of this request.

        //Obviously, this needs to be implemented. Just returning the original ctx for now...
        return processorContext
    }
}
