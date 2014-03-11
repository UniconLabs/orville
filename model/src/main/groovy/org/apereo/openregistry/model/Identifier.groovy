package org.apereo.openregistry.model

class Identifier {
    IdentifierType identifierType
    IdentifierStatus identifierStatus

    static enum IdentifierType {}
    static enum IdentifierStatus {}
}

