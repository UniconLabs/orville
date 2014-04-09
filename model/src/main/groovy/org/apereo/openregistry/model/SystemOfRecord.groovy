package org.apereo.openregistry.model

import grails.persistence.Entity
import groovy.transform.EqualsAndHashCode

/**
 * System of record
 */
@Entity
@EqualsAndHashCode
class SystemOfRecord {

    String code

    String description

    boolean active

    static constraints = {
        code unique: true
        description nullable: true, blank: true
    }
}
