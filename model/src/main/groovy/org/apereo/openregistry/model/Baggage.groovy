package org.apereo.openregistry.model

import grails.persistence.Entity
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import groovy.transform.EqualsAndHashCode

/**
 * Container for extra information about a person from a system of record
 */
@Entity
@EqualsAndHashCode
class Baggage {
    String id
    SystemOfRecord systemOfRecord
    Map<String,Object> contents
    String baggageAsString
    Type type

    Date dateCreated
    Date lastUpdated

    static constraints = {
        baggageAsString maxSize: 64000, nullable: true
        type validator: Type.typeValidator
    }

    static mapping = {
        id generator: 'uuid'
    }

    static transients = ['contents']

    def afterLoad() {
        contents = new JsonSlurper().parseText(baggageAsString) as Map<String, Object>
    }

    def beforeInsert() {
        baggageAsString = baggageToString()
    }

    def beforeUpdate() {
        baggageAsString = baggageToString()
    }

    String baggageToString() {
        new JsonBuilder().with {
            it contents
            return it
        }.toString()
    }
}
