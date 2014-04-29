package org.apereo.openregistry.service.activation

/**
 * Service responsible for activation keys related functionality
 *
 * @author Dmitriy Kopylenko
 * @author Unicon inc
 */

public interface ActivationKeyService {

    ActivationKey createAndPersist(Map<String, Object> activationKeyCreationRequest) throws IdentifierNotFoundException

    ActivationKey validate(String activationKeyValue) throws ActivationKeyNotFoundException

}