package org.apereo.openregistry.model

import grails.persistence.Entity

/**
 * A container for information about a person
 */
@Entity
class Person {
    Set<Baggage> baggage
    Set<Identifier> wallet
}
