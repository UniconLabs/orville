package org.apereo.openregistry.model

import grails.persistence.Entity

@Entity
class Name {
    String prefix
    String givenName
    String middleName
    String familyName
    String suffix
    String language

    static constraints = {
        prefix nullable: true, blank: true
        middleName nullable: true, blank: true
        familyName nullable: true, blank: true
        suffix nullable: true, blank: true
        language nullable: true, blank: true
    }
}
