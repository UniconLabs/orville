package org.apereo.openregistry.service

import groovy.transform.EqualsAndHashCode
import groovy.util.logging.Slf4j
import org.apereo.openregistry.model.TokenIdentifier
import org.apereo.openregistry.model.Type

/**
 *
 * A collection of temporary mock processors to test various processing flows from REST resource implementation while
 * no real processors have been implemented, etc. To be removed at the later stages of development...
 */
@Slf4j
abstract class MockOutcomeProcessor {

    @EqualsAndHashCode
    static class AddPersonMockOutcome_200 implements OpenRegistryProcessor {

        @Override
        OpenRegistryProcessorContext process(OpenRegistryProcessorContext processorContext) {
            log.debug("AddPersonMockOutcome_200 is executing...")
            processorContext.outcome.idMatchType = 'OK'
            return processorContext
        }
    }

    @EqualsAndHashCode
    static class AddPersonMockOutcome_201 implements OpenRegistryProcessor {

        @Override
        OpenRegistryProcessorContext process(OpenRegistryProcessorContext processorContext) {
            log.debug("AddPersonMockOutcome_201 is executing...")
            def ids = []
            processorContext.person.getTokenIdentifiers().each {
                ids << [identifier: it.token, type: it.type.value]
            }

            // 'cause they needed the sor id separate.....
            processorContext.person.tokenIdentifiers.find { it.type == Type.findByTargetAndValue(TokenIdentifier, "guest")}.with {
                ids << [identifier: it.token, type: "sor"]
            }

            processorContext.outcome.idMatchType = 'CREATED'
            processorContext.outcome.body = [referenceId: processorContext.person.tokenIdentifiers.find {it.type == Type.findByTargetAndValue(TokenIdentifier, "referenceid")}.token,
                                             identifiers: ids]
            return processorContext
        }
    }
}
