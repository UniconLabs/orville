package org.apereo.openregistry.model

import groovy.transform.EqualsAndHashCode

/**
 * Class representing a name.
 */
@EqualsAndHashCode(includeFields = true)
class Name {
    String prefix
    String givenName
    String middleName
    String familyName
    String suffix
    NameType type
    String language

    /**
     * Enum of name types
     */
    static enum NameType {
        OFFICIAL, FORMAL, ALIAS, NICKNAME, ALSO_KNOWN_AS, FORMERLY_KNOWN_AS
    }
}