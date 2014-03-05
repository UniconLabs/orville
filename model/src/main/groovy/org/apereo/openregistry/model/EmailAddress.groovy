package org.apereo.openregistry.model

/**
 * Class representing an email address
 */
class EmailAddress {
    String address
    EmailAddressType type
    boolean verified
}

/**
 * Enum of email types
 */
enum EmailAddressType {
    CAMPUS, PERSONAL
}
