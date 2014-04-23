package org.apereo.openregistry.service.standardization

import groovy.json.JsonSlurper
import org.apereo.openregistry.model.Baggage
import org.apereo.openregistry.model.Person
import org.apereo.openregistry.model.Role
import org.apereo.openregistry.model.SystemOfRecord
import org.apereo.openregistry.model.TokenIdentifier
import org.apereo.openregistry.model.Type

class GuestRoleStandardizationService implements StandardizationService {
    @Override
    Person standardize(SystemOfRecord systemOfRecord, Map request) {
        new Person().with { person ->
            person.addToWallet(new Role(
                    systemOfRecord: systemOfRecord,
                    type: Type.findByTargetAndValue(Role, "guest")
            ))
            return person
        }
    }
}
