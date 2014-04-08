package org.apereo.openregistry.model

import grails.persistence.Entity
import org.apereo.openregistry.model.Identifier

@Entity
class NameIdentifier extends Identifier {
    Type type
    Name name

    static constraints = {
        type validator: { NameIdentifier.isAssignableFrom(it.target) }
    }

    static mapping = {
        name cascade: 'all'
    }
}
