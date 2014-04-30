package org.apereo.openregistry.service.standardization

import org.apereo.openregistry.model.Role
import org.apereo.openregistry.model.Type
import org.apereo.openregistry.service.OpenRegistryProcessor
import org.apereo.openregistry.service.OpenRegistryProcessorContext

import javax.xml.bind.DatatypeConverter

class GuestRoleUpdateStandardizationProcessor implements OpenRegistryProcessor{
    @Override
    OpenRegistryProcessorContext process(OpenRegistryProcessorContext processorContext) {
        Map request = processorContext.request.contents

        // there better be a role...
        Role role = processorContext.person.wallet.find {it.type == Type.findByTargetAndValue(Role, "guest") && it.systemOfRecord == processorContext.request.systemOfRecord}
        if (!role) {
            def nPerson = new GuestRoleStandardizationService().standardize(processorContext.request.systemOfRecord, processorContext.request)
            processorContext.person << nPerson
        } else {
            if (request.sorAttributes?.sponsor) {
                def sponsorMap = request.sorAttributes.sponsor
                if (sponsorMap.type) {
                    role.sponsor.type = Type.findByTargetAndValue(Role, sponsorMap.type)
                }
                if  (sponsorMap.identifier) {
                    role.sponsor.token = sponsorMap.identifier
                }
            }
            if (request.sorAttributes?.roleEnds) {
                role.expiration = DatatypeConverter.parseDateTime((String)request.sorAttributes.roleEnds)
            }
        }

        return processorContext
    }
}
