package org.apereo.openregistry.model

import grails.persistence.Entity
import groovy.transform.EqualsAndHashCode

/**
 * A container for information about a person
 */
@Entity
@EqualsAndHashCode
class Person {
    String id
    Set<Baggage> baggage
    Set<Identifier> wallet

    Date dateCreated
    Date lastUpdated

    static hasMany = [baggage: Baggage, wallet: Identifier]

    static mapping = {
        id generator: 'uuid'
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

    /**
     * Adds that to the current person, returning a new instance
     *
     * @param that
     * @return
     */
    Person plus(Person that) {
        if (!that) {
            return this
        }
        new Person().with { person ->
            // add baggage from this and that
            [this.baggage, that.baggage].each {
                it.each {
                    person.addToBaggage(it)
                }
            }

            // add wallet from this and that
            [this.wallet, that.wallet].each {
                it.each {
                    person.addToWallet(it)
                }
            }

            return person
        }
    }

    /**
     * Modifies Person adding changes from that
     *
     * @param that
     * @return
     */
    Person leftShift(Person that) {
        if (that) {
            that.baggage.each {
                this.addToBaggage(it)
            }
            that.wallet.each {
                this.addToWallet(it)
            }
        }
        return this
    }

    /**
     * Modifies Person removing changes from that
     *
     * @param that
     * @return
     */
    Person rightShift(Person that) {
        if (that) {
            that.baggage.each {
                this.removeFromBaggage(it)
            }
            that.wallet.each {
                this.removeFromWallet(it)
            }
        }
        return this
    }

    /**
     * Business method that returns the highest baggage id (persistence id) out of all baggage entities associated
     * with this person. Useful for example in situations that this id could be used as some kind of a sequential serial number
     * representing the latest state snapshot id at any given point for this person entity.
     *
     * @return max Baggage#id from the collection of baggage entities attached to this entity instance
     */
    Long getHighestBaggageId() {
        this.baggage.collect { it.id }.max()
    }

    String getLastUpdatedDateAsUtcFormattedString() {
        //TODO: consider using javax.xml.bind.DatatypeConverter
        //TODO: or even better perhaps use Groovy extension module for Date type-wide convenience method that encapsulates this UTC formatting
        //as this is needed elsewhere e.g. AK.validThrough, etc.
        // http://groovy.codehaus.org/Creating+an+extension+module
        this.lastUpdated.format("yyyy-MM-dd'T'HH:mm:ss'Z'", TimeZone.getTimeZone('UTC'))
    }
}
