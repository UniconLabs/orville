package org.apereo.openregistry.service.activation

import org.apereo.openregistry.model.ActivationKey

/**
 *
 * Handles activation keys
 */
public interface ActivationKeyService {

    ActivationKey createFor(String identifier, String identifierType)
}