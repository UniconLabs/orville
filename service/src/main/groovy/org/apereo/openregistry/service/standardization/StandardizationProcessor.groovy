package org.apereo.openregistry.service.standardization

import groovy.transform.EqualsAndHashCode
import groovy.util.logging.Slf4j
import org.apereo.openregistry.model.Person
import org.apereo.openregistry.service.OpenRegistryProcessor
import org.apereo.openregistry.service.OpenRegistryProcessorContext

/**
 *
 * A processor implementation for standardization phase
 */
@Slf4j
@EqualsAndHashCode
class StandardizationProcessor implements OpenRegistryProcessor {

    @Override
    OpenRegistryProcessorContext process(OpenRegistryProcessorContext processorContext) {
        log.info("Starting 'standardization' processing phase with [$processorContext]")
        //TODO: Just a mock impl for now. Implement the real thing...

        processorContext.person = new Person()
        return processorContext
    }
}
