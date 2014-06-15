package org.apereo.openregistry.service

class IdOutcomeProcessor implements OpenRegistryProcessor {
    @Override
    OpenRegistryProcessorContext process(OpenRegistryProcessorContext processorContext) {
        if (!processorContext.outcome.body) {
            processorContext.outcome.body = [:]
        }
        processorContext.outcome.body.id = processorContext.person.id
        return processorContext
    }
}
