package org.apereo.openregistry.service

import static org.apereo.openregistry.model.request.OpenRegistryProcessingRequest.TYPE

/**
 *
 * Implementation of an Open Registry processor that knows how to route to different internal processing pipelines
 * (a collection of processors) based on the passed in {@link OpenRegistryProcessorContext}'s request type
 *
 * This implementation assumes that a processor in the pipeline signals the end of the processing by setting a non-null
 * <code>outcome</code> field of the <code>ProcessorContext</code>. If the end of the processors collection is reached
 * and not outcome has been set, the processor context is still returned. The calling code should then determine
 * what the system's behavior should be if no outcome is reached at the end of the processing pipeline.
 *
 * @author Dmitriy Kopylenko
 * @author Unicon inc.
 * @since 2.0
 */
class RoutingOpenRegistryProcessor implements OpenRegistryProcessor {

    final Set<OpenRegistryProcessor> addPersonProcessingPipeline

    final Set<OpenRegistryProcessor> updatePersonProcessingPipeline

    RoutingOpenRegistryProcessor(Set<OpenRegistryProcessor> addPersonProcessingPipeline, Set<OpenRegistryProcessor> updatePersonProcessingPipeline) {
        this.addPersonProcessingPipeline = addPersonProcessingPipeline
        this.updatePersonProcessingPipeline = updatePersonProcessingPipeline
    }

    @Override
    OpenRegistryProcessorContext process(OpenRegistryProcessorContext processorContext) {
        //Currently this concrete implementation 'knows' about 'add' and 'update' request types
        //and thus calls the appropriate pipeline based on that. Later more processing pipelines
        //and request types could be added and we progress through our design and implementation of the system
        //based on the new requirements

        def ctx = null
        def processAndShouldStop = { OpenRegistryProcessor p ->
            ctx = p.process(processorContext)
            ctx.outcome != null
        }
        switch(processorContext.request.type) {
            case TYPE.add:
                for(p in this.addPersonProcessingPipeline) {
                    if(processAndShouldStop(p)) {
                        break
                    }
                }
                break
            case TYPE.update:
                for(p in this.updatePersonProcessingPipeline) {
                    if(processAndShouldStop(p)) {
                        break
                    }
                }
                break
            default:
                //TODO: for now just throw a Runtime exception for unknown request types.
                throw new RuntimeException('Unknown processing request type.')
        }
        return ctx
    }
}
