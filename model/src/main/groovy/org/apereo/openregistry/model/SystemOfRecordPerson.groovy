package org.apereo.openregistry.model

/**
 * Class representing a person from a system of record. This class is the result of mapping, manipulation and validation.
 * Persisted instances of this class may have gone through each of those steps.
 */
class SystemOfRecordPerson extends Person {
    SystemOfRecord systemOfRecord
}
