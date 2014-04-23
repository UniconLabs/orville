package org.apereo.openregistry.model

import grails.persistence.Entity
import groovy.transform.EqualsAndHashCode

@Entity
@EqualsAndHashCode
class Role extends Identifier {
    Identifier sponsor
    Date expiration

    static constraints = {
        sponsor nullable: true
        expiration nullable: true
    }
}
