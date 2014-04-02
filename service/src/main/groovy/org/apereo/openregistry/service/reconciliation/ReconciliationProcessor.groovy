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
        return processorContext
    }
}
