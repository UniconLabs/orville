package org.apereo.openregistry.model

import grails.persistence.Entity
import groovy.transform.EqualsAndHashCode

@Entity
@EqualsAndHashCode
class EmailAddressIdentifier extends Identifier {

    Type type

    String emailAddress

    static constraints = {
        type validator: { val, obj ->
            EmailAddressIdentifier.isAssignableFrom(val.target)
        }

        emailAddress email: true
    }
}
