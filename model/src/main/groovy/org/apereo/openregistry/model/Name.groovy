package org.apereo.openregistry.model

/**
 * Class representing a name.
 */
class Name {
    String prefix
    String givenName
    String middleName
    String familyName
    String suffix
    NameType type
    String language
}

/**
 * Enum of name types
 */
enum NameType {
    FORMAL, ALIAS, NICKNAME, ALSO_KNOWN_AS, FORMERLY_KNOWN_AS
}