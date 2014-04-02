package org.apereo.openregistry.model

@grails.persistence.Entity
class Identifier {

    String value
    IdentifierType identifierType
    IdentifierStatus identifierStatus

    static enum IdentifierType {
        sor, enterprise
    }
    static enum IdentifierStatus {}
}

