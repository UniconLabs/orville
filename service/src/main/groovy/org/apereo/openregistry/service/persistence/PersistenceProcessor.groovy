package org.apereo.openregistry.service.persistence

import org.apereo.openregistry.model.Person
import org.apereo.openregistry.service.OpenRegistryProcessor
import org.apereo.openregistry.service.OpenRegistryProcessorContext

/**
 * A processor that persists the person
 */
class PersistenceProcessor implements OpenRegistryProcessor {
    @Override
    OpenRegistryProcessorContext process(OpenRegistryProcessorContext processorContext) {
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
