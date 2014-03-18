package org.apereo.openregistry.service.validation

import org.apereo.openregistry.model.SystemOfRecordPerson

/**
 *
 * Validation service API for {@link org.apereo.openregistry.model.SystemOfRecordPerson}s
 */
public interface PersonValidationService {

    PersonValidationResult validate(SystemOfRecordPerson systemOfRecordPerson)

}