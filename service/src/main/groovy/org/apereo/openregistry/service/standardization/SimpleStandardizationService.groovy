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
        def systemOfRecord = SystemOfRecord.findByCodeAndActive(systemOfRecordCode, true)

        Person p = new Person()
        p.baggage << new Baggage(
                systemOfRecord: systemOfRecord,
                contents: info
        )

        // do names
        info.sorAttributes?.names?.each { nameMap ->
            p.wallet.add new NameIdentifier(
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
            p.wallet.add new TokenIdentifier(
                    systemOfRecord: systemOfRecord,
                    type: Type.findByTargetAndValue(TokenIdentifier, idMap.type),
                    token: idMap.identifier
            )
        }
        // do email addresses
        info.sorAttributes?.emailAddresses?.each { emailMap ->
            p.wallet.add new EmailAddressIdentifier(
                    systemOfRecord: systemOfRecord,
                    type: Type.findByTargetAndValue(EmailAddressIdentifier, emailMap.type),
                    emailAddress: emailMap.address
            )
        }

        return p
    }

    @Override
    Person standardize(String systemOfRecordCode, String jsonBody) {
        if (!jsonBody || jsonBody == '') {
            def systemOfRecord = SystemOfRecord.findByCodeAndActive(systemOfRecordCode, true)
            return new Person(baggage: [new Baggage(systemOfRecord: systemOfRecord)])
        }
        def info = new JsonSlurper().parseText(jsonBody) as Map<String, Object>
        return standardize(systemOfRecordCode, info)
    }
}
