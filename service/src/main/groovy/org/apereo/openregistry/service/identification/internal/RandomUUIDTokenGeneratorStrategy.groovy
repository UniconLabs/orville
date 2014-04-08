package org.apereo.openregistry.service.identification.internal

import org.apereo.openregistry.service.identification.TokenGeneratorStrategy

/**
 *
 * Implementation of {@link org.apereo.openregistry.service.identification.TokenGeneratorStrategy}
 * based on random {@link UUID}.
 *
 * @author Dmitriy Kopylenko
 * @Unicon inc
 */
class RandomUUIDTokenGeneratorStrategy implements TokenGeneratorStrategy {

    @Override
    String generateToken() {
        UUID.randomUUID().toString()
    }
}
