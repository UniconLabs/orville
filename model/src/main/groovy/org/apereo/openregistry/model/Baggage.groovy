package org.apereo.openregistry.model

import grails.persistence.Entity

/**
 * Container for extra information about a person from a system of record
 */
@Entity
class Baggage {
    SystemOfRecord systemOfRecord
    Map<String,Object> baggage
}
