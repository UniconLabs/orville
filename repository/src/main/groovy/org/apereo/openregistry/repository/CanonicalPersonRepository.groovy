package org.apereo.openregistry.repository

import org.apereo.openregistry.model.OpenRegistryPerson

/**
 *
 * A Repository abstraction to persist {@link org.apereo.openregistry.model.OpenRegistryPerson}s
 */
interface CanonicalPersonRepository {

    OpenRegistryPerson insertNew(OpenRegistryPerson openRegistryPerson)

    //TODO Define other persistence methods and persistence-related business exceptions
}
