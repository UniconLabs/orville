package org.apereo.openregistry.service.calculation

import groovy.util.logging.Slf4j
import org.apereo.openregistry.model.OpenRegistryPerson
import org.apereo.openregistry.service.OpenRegistryProcessor
import org.apereo.openregistry.service.OpenRegistryProcessorContext

/**
 *
 * A processor implementation for calculation phase
 */
@Slf4j
class CalculationProcessor implements OpenRegistryProcessor {

    @Override
    OpenRegistryProcessorContext process(OpenRegistryProcessorContext processorContext) {
        log.info("Starting 'calculation' processing phase with [$processorContext]")
        //TODO: Just a mock impl for now. Implement the real thing...

        processorContext.openRegistryPerson = new OpenRegistryPerson()
        return processorContext
    }
}
