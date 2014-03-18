package org.apereo.openregistry.service.reconciliation

import groovy.util.logging.Slf4j
import org.apereo.openregistry.service.OpenRegistryProcessor
import org.apereo.openregistry.service.OpenRegistryProcessorContext

/**
 *
 * A processor implementation for reconciliation phase
 */
@Slf4j
class ReconciliationProcessor implements OpenRegistryProcessor {

    @Override
    OpenRegistryProcessorContext process(OpenRegistryProcessorContext processorContext) {
        log.info("Starting 'reconciliation' processing phase with [$processorContext]")
        //TODO implement....
        //At this stage the sor person should be available in the ctx, but no open registry person, right????
        //The 'outcome' field (Object) in the ctx could be set to any non-null value to signal to a composite processor
        //mediating the overall processing pipeline, a stop in further processing of this request.

        //Obviously, this needs to be implemented. Just returning the original ctx for now...
        return processorContext
    }
}
