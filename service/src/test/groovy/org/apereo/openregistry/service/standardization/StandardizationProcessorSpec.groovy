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
import org.apereo.openregistry.model.request.OpenRegistryProcessingRequest
import org.apereo.openregistry.service.OpenRegistryProcessorContext
import org.springframework.boot.SpringApplication
import org.springframework.context.ConfigurableApplicationContext
import spock.lang.Shared
import spock.lang.Specification

class StandardizationProcessorSpec extends Specification {
    @Shared
    StandardizationProcessor standardizationProcessor

    @Shared
    ConfigurableApplicationContext applicationContext

    @Shared
    SystemOfRecord systemOfRecord

    @Shared
    Type nameType

    @Shared
    Type emailType

    @Shared
    Type networkIdentifierType

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
        applicationContext = SpringApplication.run(TestApplication)
        def standardizationService = applicationContext.getBean(StandardizationService)
        standardizationProcessor = new StandardizationProcessor(standardizationService: standardizationService)
        systemOfRecord = new SystemOfRecord(code: "test", active: true).save()
        nameType = new Type(target: NameIdentifier, value: 'official').save()
        emailType = new Type(target: EmailAddressIdentifier, value: 'personal').save()
        networkIdentifierType = new Type(target: TokenIdentifier, value: 'network').save()
    }

    def "test processor"() {
        expect:
        def processorContext = new OpenRegistryProcessorContext(
            request: new OpenRegistryProcessingRequest(
                    body: sampleJson,
                    sor: "test"
            )
        )
        standardizationProcessor.process(processorContext)
        processorContext.person == output

        where:
        output << [new Person().with {
            addToBaggage(
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
        }]
    }
}
