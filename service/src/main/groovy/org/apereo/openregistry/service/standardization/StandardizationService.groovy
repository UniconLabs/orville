package org.apereo.openregistry.service.standardization

import org.apereo.openregistry.model.Person
import org.apereo.openregistry.model.SystemOfRecord

/**
 * Inteface for standardization.
 */
public interface StandardizationService {
    /**
     * Given a system of record code and a JSON body, returns a Person
     *
     * @param systemOfRecordCode
     * @param jsonBody
     * @return
     */
    Person standardize(String systemOfRecordCode, String jsonBody)
    Person standardize(String systemOfRecordCode, Map request)

    Person standardize(SystemOfRecord systemOfRecord, String jsonBody)
    Person standardize(SystemOfRecord systemOfRecord, Map request)
}