package org.apereo.openregistry.service.identification

/**
 *
 * Strategy API for various token generators
 *
 * @author Dmitriy Kopylenko
 * @author Unicon inc.
 */
public interface TokenGeneratorStrategy {

    /**
     * Tokens are just Strings. Implementors might choose to not generate unique tokens, but return cached value, for example.
     *
     * @return String token
     */
    String generateToken()
}