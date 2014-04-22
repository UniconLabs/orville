package org.apereo.openregistry.service.standardization

import groovy.json.JsonSlurper
import org.apereo.openregistry.model.Baggage
import org.apereo.openregistry.model.EmailAddressIdentifier
import org.apereo.openregistry.model.Name
import org.apereo.openregistry.model.NameIdentifier
import org.apereo.openregistry.model.Person
import org.apereo.openregistry.model.SystemOfRecord
import org.apereo.openregistry.model.TokenIdentifier
import org.apereo.openregistry.model.Type
import org.springframework.stereotype.Service

/**
 * A simple implementation of StandardizationService. Current implementation takes the JSON specified for the guest
 * registry and does a hard coded mapping.
 */
@Service
class SimpleStandardizationService implements StandardizationService {

    @Override
    Person standardize(String systemOfRecordCode, Map info) {
        return standardize(SystemOfRecord.findByActiveAndCode(true, systemOfRecordCode), info)
    }

    @Override
    Person standardize(String systemOfRecordCode, String jsonBody) {
        return standardize(SystemOfRecord.findByCodeAndActive(systemOfRecordCode, true), jsonBody)
    }

    @Override
    Person standardize(SystemOfRecord systemOfRecord, String jsonBody) {
        if (!jsonBody || jsonBody == '') {
            return new Person()
        }
        def info = new JsonSlurper().parseText(jsonBody) as Map<String, Object>
        return standardize(systemOfRecord, info)
    }

    @Override
    Person standardize(SystemOfRecord systemOfRecord, Map info) {
        Person p = new Person()
        // do names
        info.sorAttributes?.names?.each { nameMap ->
            p.addToWallet new NameIdentifier(
                    systemOfRecord: systemOfRecord,
                    type: Type.findByTargetAndValue(NameIdentifier, nameMap.type),
                    name: new Name(
                            prefix: nameMap.prefix,
                            givenName: nameMap.given,
                            middleName: nameMap.middle,
                            familyName: nameMap.family,
                            suffix: nameMap.suffix,
                            language: nameMap.language
                    )
            )
        }

        // do identifiers
        info.sorAttributes?.identifiers?.each { idMap ->
            p.addToWallet new TokenIdentifier(
                    systemOfRecord: systemOfRecord,
                    type: Type.findByTargetAndValue(TokenIdentifier, idMap.type),
                    token: idMap.identifier
            )
        }
        // do email addresses
        info.sorAttributes?.emailAddresses?.each { emailMap ->
            p.addToWallet new EmailAddressIdentifier(
                    systemOfRecord: systemOfRecord,
                    type: Type.findByTargetAndValue(EmailAddressIdentifier, emailMap.type),
                    emailAddress: emailMap.address
            )
        }

        return p
    }
}
