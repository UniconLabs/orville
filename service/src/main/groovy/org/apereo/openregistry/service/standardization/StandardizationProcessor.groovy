package org.apereo.openregistry.service.standardization

import groovy.transform.EqualsAndHashCode
import groovy.util.logging.Slf4j
import org.apereo.openregistry.model.Person
import org.apereo.openregistry.service.OpenRegistryProcessor
import org.apereo.openregistry.service.OpenRegistryProcessorContext
import org.springframework.beans.factory.annotation.Autowired

/**
 *
 * A processor implementation for standardization phase
 */
@Slf4j
@EqualsAndHashCode
class StandardizationProcessor implements OpenRegistryProcessor {

    @Autowired
    StandardizationService standardizationService

    @Override
    OpenRegistryProcessorContext process(OpenRegistryProcessorContext processorContext) {
        log.info("Starting 'standardization' processing phase with [$processorContext]")
        def nPerson = standardizationService.standardize(processorContext.request.systemOfRecord, processorContext.request.contents)
        if (processorContext.person) {
            processorContext.person << nPerson
        } else {
            processorContext.person = nPerson
        }
        processorContext.person.addToBaggage(processorContext.request)
        return processorContext
    }
}
