package org.apereo.openregistry.model

import grails.persistence.Entity
import groovy.transform.EqualsAndHashCode

@Entity
@EqualsAndHashCode(callSuper = true)
class TokenIdentifier extends Identifier {
    String token
}
