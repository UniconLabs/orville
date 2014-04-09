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

    static hasMany = [baggage: Baggage, wallet: Identifier]

    static mapping = {
        wallet cascade: 'all-delete-orphan'
        baggage cascade: 'all-delete-orphan'
    }

    /**
     * Helper business method to encapsulate internals of retrieval of TokenIdentifiers.
     * Returns an empty set if no identifiers are stored.
     */
    Set<TokenIdentifier> getTokenIdentifiers() {
        this.wallet.findAll() {
            TokenIdentifier.isAssignableFrom(it.class)
        } as Set<TokenIdentifier>
    }

    /**
     * Helper business method to encapsulate internals of retrieval of NameIdentifiers.
     * Returns an empty set if no identifiers are stored.
     */
    Set<NameIdentifier> getNameIdentifiers() {
        this.wallet.findAll() {
            NameIdentifier.isAssignableFrom(it.class)
        } as Set<NameIdentifier>
    }
}
