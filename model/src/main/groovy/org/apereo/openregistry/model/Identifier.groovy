package org.apereo.openregistry.model

import groovy.transform.ToString

@ToString
class Identifier {

    String value
    IdentifierType identifierType
    IdentifierStatus identifierStatus

    static enum IdentifierType {
        sor, enterprise
    }
    static enum IdentifierStatus {}
}

