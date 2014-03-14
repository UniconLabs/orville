package org.apereo.openregistry.service.standardization

import org.apereo.openregistry.model.SystemOfRecordPerson

/**
 *
 * Factory for creating standardized {@link org.apereo.openregistry.model.SystemOfRecordPerson}s
 */
public interface SystemOfRecordPersonFactory {

    SystemOfRecordPerson createFrom(Map<Object, Object> systemOfRecordPersonData)

}