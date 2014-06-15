package org.apereo.openregistry.service

import org.apereo.openregistry.model.Role
import org.apereo.openregistry.model.Type

class RoleCreationOutcomeProcessor implements OpenRegistryProcessor {
    @Override
    OpenRegistryProcessorContext process(OpenRegistryProcessorContext processorContext) {
        //TODO: make this work properly later
        def role = processorContext.person.wallet.find {it.class == Role && it.type == Type.findByTargetAndValue(Role, "guest")}
        if (!processorContext.outcome.body) {
            processorContext.outcome.body = [:]
        }
        if (!processorContext.outcome.body.identifiers) {
            processorContext.outcome.body.identifiers = []
        }
        processorContext.outcome.body.identifiers << [type: "role", identifier: role.id]
        return processorContext
    }
}
