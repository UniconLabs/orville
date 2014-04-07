package org.apereo.openregistry.model

import grails.persistence.Entity

/**
 * A container for information about a person
 */
@Entity
class Person {
    Set<Baggage> baggage = [] as Set<Baggage>
    Set<Identifier> wallet = [] as Set<Identifier>

    static hasMany = [baggage: Baggage, wallet: Identifier]

    static mapping = {
        wallet cascade: 'all-delete-orphan'
        baggage cascade: 'all-delete-orphan'
    }
}
