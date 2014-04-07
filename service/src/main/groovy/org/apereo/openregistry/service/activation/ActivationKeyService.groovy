package org.apereo.openregistry.service.activation

/**
 *
 * Handles activation keys
 */
public interface ActivationKeyService {

    Object createFor(String identifier, String identifierType)
}