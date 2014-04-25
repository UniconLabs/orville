package org.apereo.openregistry.service

import org.apereo.openregistry.model.Role

import javax.xml.bind.DatatypeConverter

class RoleOutcomeProcessor implements OpenRegistryProcessor {
    @Override
    OpenRegistryProcessorContext process(OpenRegistryProcessorContext processorContext) {
        if (!processorContext.outcome.body) {
            processorContext.outcome.body = [:]
        }

        if (!processorContext.outcome.body.roles) {
            processorContext.outcome.body.roles = []
        }

        processorContext.person.wallet.findAll { Role.isAssignableFrom it.class }.each { Role role ->
            processorContext.outcome.body.roles << [
                    id: role.id,
                    type: role.type.value,
                    expiration: DatatypeConverter.printDateTime(Calendar.getInstance().with {
                        it.time = role.expiration
                        return it
                    }),
                    sponsor: [
                            type: role.sponsor.type.value,
                            identifier: role.sponsor.token
                    ]
            ]
        }

        return processorContext
    }
}
