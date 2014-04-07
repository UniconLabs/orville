package org.apereo.openregistry.service.standardization

import groovy.json.JsonSlurper
import org.apereo.openregistry.model.EmailAddress
import org.apereo.openregistry.model.Name
import org.apereo.openregistry.model.SystemOfRecordPerson
import org.apereo.openregistry.service.OpenRegistryProcessorContext


class GormStandardizationService implements StandardizationService {
    @Override
    OpenRegistryProcessorContext standardize(OpenRegistryProcessorContext openRegistryProcessorContext) {
        openRegistryProcessorContext.systemOfRecordPerson = standardize(openRegistryProcessorContext.request.body)
        openRegistryProcessorContext.request.
        return openRegistryProcessorContext
    }

    protected SystemOfRecordPerson standardize(String input) {
        /*
        Current implementation does a simple mapping between the JSON input and the object graph. This should later be
        controlled by configuration.
         */
        def jsonObject = new JsonSlurper().parseText(input)
        def p = new SystemOfRecordPerson()
        jsonObject.sorAttributes.emailAddresses.each { it ->
            p.emailAddresses.add(new EmailAddress(address: it.address, type: EmailAddress.EmailAddressType.valueOf(it.type.toUpperCase())))
        }
        jsonObject.sorAttributes.names.each { it ->
            p.names.add(new Name(
                    type: Name.NameType.valueOf(it.type.toUpperCase()),
                    givenName: it.given,
                    familyName: it.family
            ))
        }
        return p
    }
}
