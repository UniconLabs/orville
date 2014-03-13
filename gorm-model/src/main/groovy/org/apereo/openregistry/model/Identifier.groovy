package org.apereo.openregistry.model

@grails.persistence.Entity
class Identifier {
    IdentifierType identifierType
    IdentifierStatus identifierStatus

    static enum IdentifierType {}
    static enum IdentifierStatus {}
}

