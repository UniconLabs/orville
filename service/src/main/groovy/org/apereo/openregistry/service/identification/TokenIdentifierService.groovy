package org.apereo.openregistry.service.identification

import org.apereo.openregistry.model.Person
import org.apereo.openregistry.model.SystemOfRecord
import org.apereo.openregistry.model.TokenIdentifier

/**
 *
 * Service API responsible for creating {@link org.apereo.openregistry.model.TokenIdentifier}s
 *
 * @author Dmitriy Kopylenko
 * @author Unicon inc.
 */
public interface TokenIdentifierService {

    TokenIdentifier createFor(String systemOfRecord, Person person, String identifierType)
    TokenIdentifier createFor(SystemOfRecord systemOfRecord, Person person, String identifierType)
}