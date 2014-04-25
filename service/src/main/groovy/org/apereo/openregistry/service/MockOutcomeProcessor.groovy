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
            // separate the sor Id for the person
            processorContext.person.tokenIdentifiers.find { it.type == Type.findByTargetAndValue(TokenIdentifier, "guest-sor")}.with {
                ids << [identifier: it.token, type: "sor"]
            }

            processorContext.outcome.idMatchType = 'CREATED'
            if (!processorContext.outcome.body) {
                processorContext.outcome.body = [:]
            }
            if (!processorContext.outcome.body.identifiers) {
                processorContext.outcome.body.identifiers = []
            }
            // processorContext.outcome.body = [identifiers: ids]
            processorContext.outcome.body.identifiers.addAll(ids)

            def referenceid = processorContext.person.tokenIdentifiers.find { it.type == Type.findByTargetAndValue(TokenIdentifier, "referenceid")}
            if (referenceid) {
                processorContext.outcome.body['referenceId'] = referenceid.token
            }
            return processorContext
        }
    }
}
