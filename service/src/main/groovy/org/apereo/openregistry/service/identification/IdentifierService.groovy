package org.apereo.openregistry.service.identification

import org.apereo.openregistry.model.Identifier
import org.apereo.openregistry.model.OpenRegistryPerson
import org.apereo.openregistry.model.SystemOfRecordPerson

/**
 *
 * Creates {@link org.apereo.openregistry.model.Identifier}s for canonical persons
 */
public interface IdentifierService {

    Identifier createIdentifierForOpenRegistryPerson(OpenRegistryPerson canonicalPerson, SystemOfRecordPerson systemOfRecordPerson)
}