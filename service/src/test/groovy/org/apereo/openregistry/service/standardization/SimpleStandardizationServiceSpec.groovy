package org.apereo.openregistry.service.standardization

import groovy.json.JsonSlurper
import org.apereo.openregistry.TestApplication
import org.apereo.openregistry.model.Baggage
import org.apereo.openregistry.model.EmailAddressIdentifier
import org.apereo.openregistry.model.Name
import org.apereo.openregistry.model.NameIdentifier
import org.apereo.openregistry.model.Person
import org.apereo.openregistry.model.SystemOfRecord
import org.apereo.openregistry.model.TokenIdentifier
import org.apereo.openregistry.model.Type
import org.springframework.boot.SpringApplication
import org.springframework.context.ConfigurableApplicationContext
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class SimpleStandardizationServiceSpec extends Specification {
    @Shared
    @AutoCleanup
    ConfigurableApplicationContext context

    @Shared
    StandardizationService standardizationService

    @Shared
    SystemOfRecord systemOfRecord

    @Shared
    Type nameType

    @Shared
    Type emailType

    @Shared
    Type networkIdentifierType

    @Shared
    Person fullPerson

    @Shared
    def sampleJson = '''{
  "sorAttributes":
  {
    "names":[
      {
        "type":"official",
        "given":"Pat",
        "family":"Lee"
      }
    ],
    "emailAddresses":[
      {
        "address":"pat.lee@gmail.com",
        "type":"personal"
      }
    ],
    "identifiers":[
      {
        "identifier":"pl53",
        "type":"network"
      }
    ],
    "sponsor":{
      "identifier":"12345",
      "type":"enterprise"
    },
    "roleEnds":"2014-08-04T12:00:00Z"
  }
}
'''

    void setupSpec() {
        context = SpringApplication.run(TestApplication)
        standardizationService = context.getBean(StandardizationService)
        systemOfRecord = new SystemOfRecord(code: "test", active: true).save()
        nameType = new Type(target: NameIdentifier, value: 'official').save()
        emailType = new Type(target: EmailAddressIdentifier, value: 'personal').save()
        networkIdentifierType = new Type(target: TokenIdentifier, value: 'network').save()

        fullPerson = new Person().with {
            addToBaggage new Baggage(
                    systemOfRecord: systemOfRecord,
                    contents: new JsonSlurper().parseText(sampleJson) as Map<String, Object>

            )
            addToWallet new NameIdentifier(
                    type: nameType,
                    name: new Name(
                            givenName: "Pat",
                            familyName: "Lee"
                    )
            )
            addToWallet new EmailAddressIdentifier(
                    type: emailType,
                    emailAddress: 'pat.lee@gmail.com'
            )
            addToWallet new TokenIdentifier(
                    type: networkIdentifierType,
                    token: 'pl53'
            )
            return it
        }
    }

    def "simple test"() {
        expect:
        standardizationService.standardize('test', '') == new Person(baggage: [new Baggage(systemOfRecord: systemOfRecord)])
    }

    def "simple failure test"() {
        expect:
        standardizationService.standardize('test', '') != new Person(baggage: [new Baggage(systemOfRecord: new SystemOfRecord(code: 'nottest', active: true).save())])
    }

    def "test json standardization"() {
        expect:
        def x = standardizationService.standardize('test', sampleJson)
        x == output

        where:
        output << [fullPerson]
    }

    def "test map standardization"() {
        expect:
        def map = new JsonSlurper().parseText(sampleJson)
        standardizationService.standardize('test', map) == output

        where:
        output << [fullPerson]
    }
}
