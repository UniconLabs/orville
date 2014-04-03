package org.apereo.openregistry.processor.gorm

import org.apereo.openregistry.model.SystemOfRecordPerson
import org.apereo.openregistry.service.OpenRegistryProcessor
import org.apereo.openregistry.service.OpenRegistryProcessorContext

class StandardizationProcessor implements OpenRegistryProcessor {
    @Override
    OpenRegistryProcessorContext process(OpenRegistryProcessorContext processorContext) {
        // TODO: implement
        processorContext.request.body
        def person = new SystemOfRecordPerson()

        return processorContext
    }
}
