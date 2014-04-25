package org.apereo.openregistry.service.standardization

import groovy.json.JsonSlurper
import org.apereo.openregistry.model.Baggage
import org.apereo.openregistry.model.Person
import org.apereo.openregistry.model.Role
import org.apereo.openregistry.model.SystemOfRecord
import org.apereo.openregistry.model.TokenIdentifier
import org.apereo.openregistry.model.Type

import javax.xml.bind.DatatypeConverter

class GuestRoleStandardizationService implements StandardizationService {
    @Override
    Person standardize(SystemOfRecord systemOfRecord, Map request) {
        def expiration = DatatypeConverter.parseDateTime((String)request.sorAttributes?.roleEnds).time
        def sponsor = new TokenIdentifier(
                systemOfRecord: systemOfRecord,
                type: Type.findByTargetAndValue(TokenIdentifier, request.sorAttributes?.sponsor?.type),
                token: request.sorAttributes?.sponsor?.identifier
        )
        new Person().with { person ->
            person.addToWallet(new Role(
                    systemOfRecord: systemOfRecord,
                    type: Type.findByTargetAndValue(Role, "guest"),
                    expiration: expiration,
                    sponsor: sponsor
            ))
            return person
        }
    }
}
