package org.apereo.openregistry.service.standardization

import org.apereo.openregistry.model.Person

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
}