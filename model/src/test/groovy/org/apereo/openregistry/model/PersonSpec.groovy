package org.apereo.openregistry.model

import org.apereo.openregistry.TestApplication
import org.springframework.boot.SpringApplication
import org.springframework.context.ConfigurableApplicationContext
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class PersonSpec extends Specification {
    @Shared
    @AutoCleanup
    ConfigurableApplicationContext context

    void setupSpec() {
        context = SpringApplication.run(TestApplication)
    }

    @Unroll
    def "test left shift"() {
        expect:
        def x = a << b
        x == c && a.is(x)
        where:
        a << [new Person().addToWallet(new TokenIdentifier(token: "test")), new Person(), new Person(), new Person().addToWallet(new TokenIdentifier(token: "test"))]
        b << [new Person().addToWallet(new TokenIdentifier(token: "me")), new Person(), new Person().addToWallet(new TokenIdentifier(token: "test")), new Person()]
        c << [new Person().addToWallet(new TokenIdentifier(token: "test")).addToWallet(new TokenIdentifier(token: "me")), new Person(), new Person().addToWallet(new TokenIdentifier(token: "test")), new Person().addToWallet(new TokenIdentifier(token: "test"))]
    }
}
