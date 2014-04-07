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

    static constraints = {
        code unique: true
        description nullable: true, blank: true
    }
}
