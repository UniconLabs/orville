package org.apereo.openregistry.service.standardization

import org.apereo.openregistry.service.OpenRegistryProcessor
import org.apereo.openregistry.service.OpenRegistryProcessorContext

class GuestRoleStandardizationProcessor implements OpenRegistryProcessor {
    GuestRoleStandardizationService guestRoleStandardizationService

    @Override
    OpenRegistryProcessorContext process(OpenRegistryProcessorContext processorContext) {
        def nPerson = guestRoleStandardizationService.standardize(processorContext.request.systemOfRecord, processorContext.request.contents)
        if (processorContext.person) {
            processorContext.person << nPerson
        } else {
            processorContext.person = nPerson
        }
        return processorContext
    }
}
