package org.apereo.openregistry.model

/**
 * Class representing a role in Open Registry
 */
@grails.persistence.Entity
class OpenRegistryRole extends Role {
    Role systemOfRecordRole
}
