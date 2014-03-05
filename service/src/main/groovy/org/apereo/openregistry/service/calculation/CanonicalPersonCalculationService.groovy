package org.apereo.openregistry.service.calculation

import org.apereo.openregistry.model.OpenRegistryPerson
import org.apereo.openregistry.model.SystemOfRecordPerson

/**
 *
 * Calculates and creates canonical {@link org.apereo.openregistry.model.OpenRegistryPerson}s from given
 * {@link org.apereo.openregistry.model.SystemOfRecordPerson}s
 */
public interface CanonicalPersonCalculationService {

    OpenRegistryPerson calculateCanonicalPersonFrom(SystemOfRecordPerson systemOfRecordPerson)
}