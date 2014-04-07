package org.apereo.openregistry.service.standardization

import org.apereo.openregistry.model.EmailAddress
import org.apereo.openregistry.model.Name
import org.apereo.openregistry.model.Role
import org.apereo.openregistry.model.SystemOfRecordPerson
import spock.lang.Specification

class GormStandardizationServiceSpecification extends Specification {
    def standardizationService = new GormStandardizationService()

    def "simple standardization test"() {
        expect:
            new SystemOfRecordPerson() == standardizationService.standardize('{"sorAttributes":{"names":[], "emailAddresses":[]}}')
    }

    def "standardization test"() {
        expect:
        def e = standardizationService.standardize(input)
        println "here: ${output.hashCode()}: ${e.hashCode()}"
        output.names == e.names
        where:
        input << ['''
{
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
''']
        output << [new SystemOfRecordPerson(
                emailAddresses: [
                        new EmailAddress(address: 'pat.lee@gmail.com',
                                type: EmailAddress.EmailAddressType.PERSONAL)
                ] as Set<EmailAddress>,
                names: [
                        new Name(
                                type: Name.NameType.OFFICIAL,
                                givenName: 'Pat',
                                familyName: 'Lee'
                        )
                ] as Set<Name>,
                roles: [
                        new Role(

                        )
                ] as Set<Role>
        )]
    }

    def "standardization failure test"() {
        expect:
        def e = standardizationService.standardize(input)
        println "here: ${e.names.equals(output.names)}"
        output != e
        where:
        input << ['''
{
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
''']
        output << [new SystemOfRecordPerson(
                emailAddresses: [
                        new EmailAddress(address: 'pat.lee@gmail.com',
                                type: EmailAddress.EmailAddressType.PERSONAL)
                ] as Set<EmailAddress>,
                names: [
                        new Name(
                                type: Name.NameType.OFFICIAL,
                                givenName: 'Patricia',
                                familyName: 'Lee'
                        )
                ] as Set<Name>
        )]
    }
}
