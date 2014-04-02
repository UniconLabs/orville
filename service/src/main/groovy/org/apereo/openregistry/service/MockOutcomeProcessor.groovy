package org.apereo.openregistry.service

import groovy.util.logging.Slf4j

/**
 *
 * A collection of temporary mock processors to test various processing flows from REST resource implementation while
 * no real processors have been implemented, etc. To be removed at the later stages of development...
 */
@Slf4j
abstract class MockOutcomeProcessor {

    static class AddPersonMockOutcome_200 implements OpenRegistryProcessor {

        @Override
        OpenRegistryProcessorContext process(OpenRegistryProcessorContext processorContext) {
            log.debug("AddPersonMockOutcome_200 is executing...")
            processorContext.outcome.idMatchType = 'OK'
            return processorContext
        }
    }

    static class AddPersonMockOutcome_201 implements OpenRegistryProcessor {

        @Override
        OpenRegistryProcessorContext process(OpenRegistryProcessorContext processorContext) {
            log.debug("AddPersonMockOutcome_201 is executing...")
            def createdBody = [referenceId: 'M225127891',
                               identifiers: [
                                       [identifier: 'GUEST000797031', type: 'sor'],
                                       [identifier: '103459', type: 'enterprise']
                               ]]
            processorContext.outcome.idMatchType = 'CREATED'
            processorContext.outcome.body = createdBody
            return processorContext
        }
    }
}
