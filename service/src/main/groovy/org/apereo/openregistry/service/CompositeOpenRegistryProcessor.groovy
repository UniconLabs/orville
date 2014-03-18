package org.apereo.openregistry.service

import static org.apereo.openregistry.model.request.OpenRegistryProcessingRequest.TYPE

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
 * @since 2.0
 */
class CompositeOpenRegistryProcessor implements OpenRegistryProcessor {

    final Set<OpenRegistryProcessor> processingPipeline

    CompositeOpenRegistryProcessor(Set<OpenRegistryProcessor> processingPipeline) {
        this.processingPipeline = processingPipeline
    }

    @Override
    OpenRegistryProcessorContext process(OpenRegistryProcessorContext processorContext) {
        def ctx = null
        for (p in this.processingPipeline) {
            ctx = p.process(processorContext)
            if (ctx.outcome) {
                break
            }
        }
        return ctx
    }
}
