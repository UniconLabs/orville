package org.apereo.openregistry.service.activation.internal

import groovy.util.logging.Slf4j
import org.apereo.openregistry.model.TokenIdentifier
import org.apereo.openregistry.model.Type
import org.apereo.openregistry.service.activation.ActivationKey
import org.apereo.openregistry.model.Identifier
import org.apereo.openregistry.service.activation.ActivationKeyNotFoundException
import org.apereo.openregistry.service.activation.ActivationKeyService
import org.apereo.openregistry.service.activation.IdentifierNotFoundException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

/**
 *
 * Default implementation of {@link org.apereo.openregistry.service.activation.ActivationKeyService}
 *
 * Uses GORM to persistence.
 *
 * @author Dmitriy Kopylenko
 * @author Unicon inc
 */
@Service
@Slf4j
class DefaultActivationKeyService implements ActivationKeyService {

    @Value('${activationKey.validity.period.days}')
    int activationKeyValidityPeriodInDays

    @Override
    ActivationKey createAndPersist(Map<String, Object> request) {
        def idType = request.identifier.type
        def idValue = request.identifier.identifier
        Identifier identifier = TokenIdentifier.findByTypeAndToken(Type.findByTargetAndValue(TokenIdentifier, idType), idValue)

        if (!identifier) {
            throw new IdentifierNotFoundException("Identifier '$idValue:$idType' is not found.")
        }
        log.debug("Found the following Identifier: $identifier")

        def activationKey
        ActivationKey.withTransaction {
            activationKey = ActivationKey.findByIdentifier(identifier)
            if (activationKey) {
                activationKey.delete(flush: true)
            }
            activationKey = new ActivationKey(identifier: identifier, validThroughDate: new Date() + this.activationKeyValidityPeriodInDays).save()
            log.debug("Persisted the following activation key: $activationKey")
        }

        return activationKey
    }

    @Override
    ActivationKey validate(String activationKeyValue) {
        def activationKey = ActivationKey.findById(activationKeyValue)
        if (!activationKey || !activationKey.isValid()) {
            throw new ActivationKeyNotFoundException("Activation key '$activationKeyValue' is not valid.")
        }
        return activationKey.with {
            it.used = true
            it.save(flush: true)
            return it
        }
    }
}
