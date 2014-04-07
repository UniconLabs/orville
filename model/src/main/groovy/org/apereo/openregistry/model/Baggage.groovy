package org.apereo.openregistry.model

/**
 * Container for extra information about a person from a system of record
 */
class Baggage {
    SystemOfRecord systemOfRecord
    Map<String,Object> baggage
}
