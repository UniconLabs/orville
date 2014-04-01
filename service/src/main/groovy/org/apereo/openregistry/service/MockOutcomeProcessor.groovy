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
            processorContext.outcome = [idMatchType:'OK']
            return processorContext
        }
    }
}
