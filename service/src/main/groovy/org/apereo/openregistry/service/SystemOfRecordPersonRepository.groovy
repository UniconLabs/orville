package org.apereo.openregistry.service

import org.apereo.openregistry.model.SystemOfRecordPerson

/**
 *
 * Repository abstraction for {@link org.apereo.openregistry.model.SystemOfRecordPerson} model
 */
public interface SystemOfRecordPersonRepository {

    /**
     * Find a {@link SystemOfRecordPerson} for a given SOR and SOR_ID
     * @param sor
     * @param sorId
     * @return a valid person object or null if not found
     */
    SystemOfRecordPerson findBySystemOfRecordAndSystemOfRecordId(String sor, String sorId)
}