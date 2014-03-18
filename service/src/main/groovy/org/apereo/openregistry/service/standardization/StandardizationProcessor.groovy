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
        //As we will defer the impl of 'update' for later, the data available in processor context for 'add person' is
        //[sor -> and system of record name/id, body -> raw data in the form of Map<String, Object>, type -> ProcessorContext.TYPE.add]

        //Obviously, this needs to be implemnted. Just returning the original ctx for now...
        return processorContext
    }
}
