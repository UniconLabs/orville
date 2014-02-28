package org.apereo.openregistry.model

class EmailAddress {
    String address
    EmailAddressType type
    boolean verified
}

enum EmailAddressType {
    CAMPUS, PERSONAL
}
