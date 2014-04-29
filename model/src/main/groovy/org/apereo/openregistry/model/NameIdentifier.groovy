package org.apereo.openregistry.model

import grails.persistence.Entity
import groovy.transform.EqualsAndHashCode
import org.apereo.openregistry.model.Identifier

@Entity
@EqualsAndHashCode(callSuper = true)
class NameIdentifier extends Identifier {
    Name name

    static mapping = {
        name cascade: 'all'
    }
}
