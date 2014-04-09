package org.apereo.openregistry.service.identification.internal

import groovy.transform.Immutable
import org.apereo.openregistry.model.Person
import org.apereo.openregistry.model.SystemOfRecord
import org.apereo.openregistry.model.TokenIdentifier
import org.apereo.openregistry.model.Type
import org.apereo.openregistry.service.identification.TokenGeneratorStrategy
import org.apereo.openregistry.service.identification.TokenIdentifierService

/**
 * <code>TokentIdentifierService</code> implementation responsible for creating <code>TokenIdentifier</code>s on behalf of
 * any particular system of record.
 *
 * @author Dmitriy Kopylenko
 * @author Unicon inc
 */
class SystemOfRecordTokenIdentifierService implements TokenIdentifierService {

    final String prefix

    final TokenGeneratorStrategy tokenGeneratorStrategy

    SystemOfRecordTokenIdentifierService(String prefix, TokenGeneratorStrategy tokenGeneratorStrategy) {
        this.prefix = prefix
        this.tokenGeneratorStrategy = tokenGeneratorStrategy
    }

    @Override
    TokenIdentifier createFor(String systemOfRecord, Person person, String tokenIdentifierType) {
        new TokenIdentifier(
                systemOfRecord: SystemOfRecord.findByCode(systemOfRecord) ?: new SystemOfRecord(code: systemOfRecord, active: true),
                token: this.prefix + this.tokenGeneratorStrategy.generateToken(),
                type: Type.findByTargetAndValue(TokenIdentifier, tokenIdentifierType) ?: new Type(target: TokenIdentifier, value: tokenIdentifierType))
    }
}
