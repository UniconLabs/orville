package org.apereo.openregistry.service

import groovy.transform.InheritConstructors
import org.apereo.openregistry.model.OpenRegistryPerson
import org.apereo.openregistry.model.SystemOfRecordPerson

/**
 * Simple sample implementation of a processor pipeline
 */
class ProcessorPipeline implements Processor {
    List<Processor> processors

    @Override
    ProcessorContext process(ProcessorContext processorContext) throws ProcessorException {
        def p2 = processorContext
        for (Processor processor: processors) {
            p2 = processor.process(p2)
        }
        return p2
    }
}

interface Processor {
    ProcessorContext process(ProcessorContext processorContext) throws ProcessorException
}

class ProcessorContext {
    Object request
    SystemOfRecordPerson systemOfRecordPerson
    OpenRegistryPerson openRegistryPerson
}

@InheritConstructors
class ProcessorException extends Exception {}