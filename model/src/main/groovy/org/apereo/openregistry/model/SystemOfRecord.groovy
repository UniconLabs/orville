package org.apereo.openregistry.model

import grails.persistence.Entity

/**
 * System of record
 */
@Entity
class SystemOfRecord {

    String code

    String description

    boolean active
}
