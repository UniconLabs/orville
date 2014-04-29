package org.apereo.openregistry.model

import grails.persistence.Entity
import groovy.transform.EqualsAndHashCode

@Entity
@EqualsAndHashCode(callSuper = true)
class EmailAddressIdentifier extends Identifier {
    String emailAddress

    static constraints = {
        emailAddress email: true
    }
}
