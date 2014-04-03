package org.apereo.openregistry.model

import groovy.transform.EqualsAndHashCode

/**
 * Class representing a canonical name as calculated by Open Registry.
 */
@EqualsAndHashCode(includeFields = true, callSuper = true)
@grails.persistence.Entity
class OpenRegistryPerson extends Person {
    Set<SystemOfRecordPerson> systemOfRecordPersons
}
