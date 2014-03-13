package org.apereo.openregistry.model

/**
 * Class representing a canonical name as calculated by Open Registry.
 */
@grails.persistence.Entity
class OpenRegistryPerson extends Person {
    Set<SystemOfRecordPerson> systemOfRecordPersons
}
