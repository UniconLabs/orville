package org.apereo.openregistry.model

import grails.persistence.Entity
import groovy.transform.EqualsAndHashCode

/**
 * A container for information about a person
 */
@Entity
@EqualsAndHashCode
class Person {
    Set<Baggage> baggage = [] as Set<Baggage>
    Set<Identifier> wallet = [] as Set<Identifier>

    Date dateCreated
    Date lastUpdated

    static hasMany = [baggage: Baggage, wallet: Identifier]

    static mapping = {
        wallet cascade: 'all-delete-orphan'
        baggage cascade: 'all-delete-orphan'
    }
}
