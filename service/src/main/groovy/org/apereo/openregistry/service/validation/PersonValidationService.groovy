package org.apereo.openregistry.service.validation

import org.apereo.openregistry.model.Person


/**
 *
 * Validation service API for Person
 */
public interface PersonValidationService {

    PersonValidationResult validate(Person person)

}