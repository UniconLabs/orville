package org.apereo.openregistry.service

import groovy.transform.InheritConstructors
import org.apereo.openregistry.model.OpenRegistryPerson
import org.apereo.openregistry.model.SystemOfRecordPerson

/**
 * Simple sample implementation of a processor pipeline
 */
class OpenRegistryProcessorPipeline implements OpenRegistryProcessor {
    List<OpenRegistryProcessor> processors

    @Override
    OpenRegistryProcessorContext process(OpenRegistryProcessorContext processorContext) throws OpenRegistryProcessorException {
        def p2 = processorContext
        for (OpenRegistryProcessor processor: processors) {
            p2 = processor.process(p2)
        }
        return p2
    }
}

interface OpenRegistryProcessor {
    OpenRegistryProcessorContext process(OpenRegistryProcessorContext processorContext) throws OpenRegistryProcessorException
}

abstract class IdempotentOpenRegistryProcessor implements OpenRegistryProcessor {
    @Override
    OpenRegistryProcessorContext process(OpenRegistryProcessorContext processorContext) throws OpenRegistryProcessorException {
        processorContext.processors.contains(this.class) ? processorContext : doProcess(processorContext)
    }

    abstract OpenRegistryProcessorContext doProcess(OpenRegistryProcessorContext processorContext) throws OpenRegistryProcessorException
}

class OpenRegistryProcessorContext {
    Object request
    //TODO: this could be used to make routing decisions down the processor chain e.g. short circuit the processing based on some specific outcome, etc.
    // as well as calling client code decisions based on the overall outcome of the processing pipeline
    Object outcome
    SystemOfRecordPerson systemOfRecordPerson
    OpenRegistryPerson openRegistryPerson
    Set<Class> processors
}

@InheritConstructors
class OpenRegistryProcessorException extends RuntimeException {}