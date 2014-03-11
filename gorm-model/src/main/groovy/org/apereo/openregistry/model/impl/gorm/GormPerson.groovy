package org.apereo.openregistry.model.impl.gorm

import grails.persistence.Entity
import org.apereo.openregistry.model.Person

@Entity
class GormPerson extends Person {
    static constraints = {
        dateOfBirth nullable: true
    }
}
