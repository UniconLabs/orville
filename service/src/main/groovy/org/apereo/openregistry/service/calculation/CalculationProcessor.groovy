package org.apereo.openregistry.service.calculation

import org.apereo.openregistry.service.OpenRegistryProcessor
import org.apereo.openregistry.service.OpenRegistryProcessorContext

/**
 *
 * A processor implementation for calculation phase
 */
class CalculationProcessor implements OpenRegistryProcessor {

    @Override
    OpenRegistryProcessorContext process(OpenRegistryProcessorContext processorContext) {
        log.info("Starting 'calculation' processing phase with [$processorContext]")
        //TODO implement....
        //At this stage the sor person should be available in the ctx, but no open registry person, right????
        //This is where 'new' or 'calculated' OpenRegistryPerson instance is born????
        //The 'outcome' field (Object) in the ctx could be set to any non-null value to signal to a composite processor
        //mediating the overall processing pipeline, a stop in further processing of this request.

        //Obviously, this needs to be implemented. Just returning the original ctx for now...
        return processorContext
    }
}
