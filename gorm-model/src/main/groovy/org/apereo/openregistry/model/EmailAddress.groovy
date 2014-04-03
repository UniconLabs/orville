package org.apereo.openregistry.model

import groovy.transform.EqualsAndHashCode

/**
 * Class representing an email address
 */
@EqualsAndHashCode(includeFields = true)
@grails.persistence.Entity
class EmailAddress {
    String address
    EmailAddressType type
    boolean verified

    /**
     * Enum of email types
     */
    static enum EmailAddressType {
        CAMPUS, PERSONAL
    }
}
