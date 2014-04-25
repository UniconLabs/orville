package org.apereo.openregistry.service.persistence

import groovy.transform.EqualsAndHashCode
import groovy.util.logging.Slf4j
import org.apereo.openregistry.model.Person
import org.apereo.openregistry.service.OpenRegistryProcessor
import org.apereo.openregistry.service.OpenRegistryProcessorContext

/**
 * A processor that persists the person
 */
@EqualsAndHashCode
@Slf4j
class PersistenceProcessor implements OpenRegistryProcessor {
    @Override
    OpenRegistryProcessorContext process(OpenRegistryProcessorContext processorContext) {
        log.info("Starting 'persistence' processing phase with [$processorContext]")

        if (processorContext.person) {
            Person.withTransaction {
                def p = processorContext.person.save()
                if(p) {
                    processorContext.person = p
                }
                else {
                    //TODO: need more robust validation error handling than this
                    processorContext.person.errors.each {
                        println "ERROR ===============> ${it.fieldError.toString()}"
                    }
                }

            }
        }
        return processorContext
    }
}
