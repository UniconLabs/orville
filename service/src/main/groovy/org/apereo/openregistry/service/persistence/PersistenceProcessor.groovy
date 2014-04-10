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
                processorContext.person = processorContext.person.save()
            }
        }
        return processorContext
    }
}
