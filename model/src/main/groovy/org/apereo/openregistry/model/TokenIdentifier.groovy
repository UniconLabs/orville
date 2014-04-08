package org.apereo.openregistry.model

import grails.persistence.Entity

@Entity
class TokenIdentifier extends Identifier {
    Type type
    String token

    static constraints = {
        type validator: { type.target == TokenIdentifier }
    }
}
