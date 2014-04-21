package org.apereo.openregistry.model

import grails.persistence.Entity
import groovy.transform.EqualsAndHashCode

@Entity
@EqualsAndHashCode
class Type {
    Class target
    String value

    static constraints = {
        value unique: 'target'
    }

    static typeValidator = { val, obj ->
        obj.class.isAssignableFrom(val.target)
    }
}
