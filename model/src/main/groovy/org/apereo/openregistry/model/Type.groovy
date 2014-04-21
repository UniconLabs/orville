package org.apereo.openregistry.model

import grails.persistence.Entity
import groovy.transform.EqualsAndHashCode

@Entity
@EqualsAndHashCode
class Type {
    String target
    String value

    Class targetClass

    static constraints = {
        value unique: 'target'
    }

    static transients = ['targetClass']

    Class getTargetClass() {
        if (target.startsWith("class ")) {
            return Class.forName(target.substring(6))
        } else {
            return Class.forName(target)
        }
    }

    static typeValidator = { Type val, obj ->
        return obj.class.isAssignableFrom(val.targetClass)
    }
}
