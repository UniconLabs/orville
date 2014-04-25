package org.apereo.openregistry.service.idmatch

import org.apereo.openregistry.model.Baggage
import org.apereo.openregistry.model.SystemOfRecord
import org.apereo.openregistry.model.TokenIdentifier
import org.apereo.openregistry.model.Type
import org.apereo.openregistry.service.OpenRegistryProcessor
import org.apereo.openregistry.service.OpenRegistryProcessorContext

class IdMatchProcessor implements OpenRegistryProcessor {
    IdMatchService idMatchService

    @Override
    OpenRegistryProcessorContext process(OpenRegistryProcessorContext processorContext) {
        // TODO: go back and really do this
        // initially just assume that everything is new, and we're just doing guest for now
        def ids = processorContext.person.tokenIdentifiers
        TokenIdentifier identifier = processorContext.person.tokenIdentifiers.find {it.type.value == 'guest-sor'}
        def response = idMatchService.putIdMatchServiceResponse(processorContext.person, "guest", identifier.token)
        if (!response) {
            throw new IllegalStateException("empty response")
        }
        def sor = SystemOfRecord.findByCodeAndActive("idmatch", true)
        processorContext.person.addToWallet new TokenIdentifier(systemOfRecord: sor, type: Type.findByTargetAndValue(TokenIdentifier, "referenceid"), token: response.referenceId)
        //TODO: define proper idMatch baggage type
        processorContext.person.addToBaggage new Baggage(systemOfRecord: sor, contents: response.fullResponse, type: Type.findByTargetAndValue(Baggage, "add"))
        processorContext.outcome.idMatchType = response.status

        return processorContext
    }
}
