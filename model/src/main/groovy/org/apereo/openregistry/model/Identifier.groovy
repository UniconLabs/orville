package org.apereo.openregistry.model

import grails.persistence.Entity
import groovy.transform.EqualsAndHashCode

/**
 * A person's identifier from a system of record
 */
@Entity
@EqualsAndHashCode
class Identifier {
    String id
    Type type
    SystemOfRecord systemOfRecord

    static belongsTo = [person: Person]

    static constraints = {
        type validator: Type.typeValidator
        person nullable: true
    }

    static mapping = {
        tablePerSubclass true
        id generator: 'uuid'
    }

    Date dateCreated
    Date lastUpdated
}
