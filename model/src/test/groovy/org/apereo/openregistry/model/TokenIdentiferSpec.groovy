package org.apereo.openregistry.model

import org.apereo.openregistry.TestApplication
import org.springframework.boot.SpringApplication
import org.springframework.context.ConfigurableApplicationContext
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class TokenIdentiferSpec extends Specification {
    @Shared
    @AutoCleanup
    ConfigurableApplicationContext context

    void setupSpec() {
        context = SpringApplication.run(TestApplication)
    }

    @Unroll
    def "test for equality"() {
        expect:
        a.wallet[0] == b.wallet[0]
        where:
        a                                                                                                                      | b
        new Person().addToWallet(new TokenIdentifier())                                                                        | new Person().addToWallet(new TokenIdentifier())
        new Person().addToWallet(new TokenIdentifier(token: "testme"))                                                         | new Person().addToWallet(new TokenIdentifier(token: "testme"))
        new Person().addToWallet(new TokenIdentifier(type: new Type(target: TokenIdentifier, value: "test"), token: "testme")) | new Person().addToWallet(new TokenIdentifier(type: new Type(target: TokenIdentifier, value: "test"), token: "testme"))
    }

    @Unroll
    def "test for inequality"() {
        expect:
        a != b
        where:
        a                                                                                             | b
        new TokenIdentifier(token: "testme")                                                          | new TokenIdentifier()
        new TokenIdentifier(type: new Type(target: TokenIdentifier, value: "typeq"), token: "testme") | new TokenIdentifier(type: new Type(target: TokenIdentifier, value: "typep"), token: "testme")
    }
}
