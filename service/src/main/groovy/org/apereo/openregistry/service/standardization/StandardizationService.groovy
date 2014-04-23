package org.apereo.openregistry.service.standardization

import org.apereo.openregistry.model.Person
import org.apereo.openregistry.model.SystemOfRecord

/**
 * Inteface for standardization.
 */
public interface StandardizationService {
    /**
     * Given a SystemOfRecord and a request Map returns a Person. The service should map the json map to propeties of
     * Person.
     *
     * @param systemOfRecord
     * @param request
     * @return
     */
    Person standardize(SystemOfRecord systemOfRecord, Map request)
}