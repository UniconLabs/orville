package org.apereo.openregistry.service

import groovy.util.logging.Slf4j

/**
 *
 * Implementation of an Open Registry processor that delegates processing to an internal collection of {@link OpenRegistryProcessor}s
 * a.k.a. <i>processing pipeline</i>
 *
 * This implementation assumes that a processor in the pipeline signals the end of the processing by setting a non-null
 * <code>outcome</code> field of the <code>ProcessorContext</code>. If the end of the processors collection is reached
 * and no outcome has been set, the processor context is still returned. The calling code should then determine
 * what the system's behavior should be if no outcome is reached at the end of the processing pipeline.
 *
 * @author Dmitriy Kopylenko
 * @author Unicon inc.
 */
@Slf4j
class CompositeOpenRegistryProcessor implements OpenRegistryProcessor {

    final Set<OpenRegistryProcessor> processingPipeline

    CompositeOpenRegistryProcessor(Set<OpenRegistryProcessor> processingPipeline) {
        this.processingPipeline = processingPipeline
    }

    @Override
    OpenRegistryProcessorContext process(OpenRegistryProcessorContext processorContext) {
        log.debug("Processing request: {} with the following processing pipeline: {}", processorContext, this.processingPipeline)
        def ctx = null
        for (p in this.processingPipeline) {
            ctx = p.process(processorContext)
            if (ctx.outcome) {
                log.info(" Processor '{}' set a non-null outcome signaling the end of processing. Returning processor context: {}", p, ctx)
                return ctx
            }
        }
        log.info("Reached the end of the processing pipeline, but no outcome has been set by any processor. Returning processor context: {}", ctx)
        return ctx
    }
}