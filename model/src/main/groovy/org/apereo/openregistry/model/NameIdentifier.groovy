package org.apereo.openregistry.model

import grails.persistence.Entity
import groovy.transform.EqualsAndHashCode
import org.apereo.openregistry.model.Identifier

@Entity
@EqualsAndHashCode
class NameIdentifier extends Identifier {
    Type type
    Name name

    static constraints = {
        type validator: Type.typeValidator
    }

    static mapping = {
        name cascade: 'all'
    }
}
