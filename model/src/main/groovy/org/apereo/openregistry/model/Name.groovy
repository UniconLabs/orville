package org.apereo.openregistry.model

class Name {
    String prefix
    String givenName
    String middleName
    String familyName
    String suffix
    NameType type
    String language
}

enum NameType {
    FORMAL, ALIAS, NICKNAME, ALSO_KNOWN_AS, FORMERLY_KNOWN_AS
}