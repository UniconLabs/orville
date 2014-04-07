package org.apereo.openregistry.model

import grails.persistence.Entity
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper

/**
 * Container for extra information about a person from a system of record
 */
@Entity
class Baggage {
    SystemOfRecord systemOfRecord
    Map<String,Object> baggage
    String baggageAsString

    static constraints = {
        baggageAsString maxSize: 64000
    }

    static transients = ['baggage']

    def afterLoad() {
        baggage = new JsonSlurper().parseText(baggageAsString)
    }

    def beforeInsert() {
        baggageAsString = baggageToString()
    }

    def beforeUpdate() {
        baggageAsString = baggageToString()
    }

    String baggageToString() {
        new JsonBuilder().with {
            it baggage
            return it
        }.toString()
    }
}
