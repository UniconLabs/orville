package org.apereo.openregistry.model

import grails.persistence.Entity

/**
 * A person's identifier from a system of record
 */
@Entity
class Identifier {
    SystemOfRecord systemOfRecord
    Object info
}
