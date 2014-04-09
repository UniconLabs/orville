package org.apereo.openregistry.model

import grails.persistence.Entity
import groovy.transform.EqualsAndHashCode

/**
 * A person's identifier from a system of record
 */
@Entity
@EqualsAndHashCode
class Identifier {
    SystemOfRecord systemOfRecord

    Date dateCreated
    Date lastUpdated
}
