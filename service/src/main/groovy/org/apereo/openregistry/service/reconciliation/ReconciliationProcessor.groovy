package org.apereo.openregistry.service.reconciliation

import groovy.transform.EqualsAndHashCode
import groovy.util.logging.Slf4j
import org.apereo.openregistry.model.OpenRegistryPerson
import org.apereo.openregistry.service.OpenRegistryProcessor
import org.apereo.openregistry.service.OpenRegistryProcessorContext

/**
 *
 * A processor implementation for reconciliation phase
 */
@Slf4j
@EqualsAndHashCode
class ReconciliationProcessor implements OpenRegistryProcessor {

    @Override
    OpenRegistryProcessorContext process(OpenRegistryProcessorContext processorContext) {
        log.info("Starting 'reconciliation' processing phase with [$processorContext]")
        //TODO implement the real thing

        processorContext.openRegistryPerson = new OpenRegistryPerson()
        return processorContext
    }
}
