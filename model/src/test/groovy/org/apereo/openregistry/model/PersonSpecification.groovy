package org.apereo.openregistry.model

import spock.lang.Specification

class PersonSpecification extends Specification{
    def "pointless test 1"() {
        expect:
            def x = new Person(names: [new Name(givenName: "test")] as Set)
            def y = new Person(names: [new Name(givenName: "me")] as Set)
            x != y
    }

    def "pointless test 2"() {
        expect:
            def x = new Person(names: [new Name(givenName: "test")] as Set)
            def y = new Person(names: [new Name(givenName: "test")] as Set)
            x == y
    }

    def "pointless test 3"() {
        expect:
            def x = new SystemOfRecordPerson(names: [new Name(givenName: "test")] as Set)
            def y = new SystemOfRecordPerson(names: [new Name(givenName: "another")] as Set)
            x != y
    }

    def "pointless test 4"() {
        expect:
            def x = new SystemOfRecordPerson(names: [new Name(givenName: "test")] as Set)
            def y = new SystemOfRecordPerson(names: [new Name(givenName: "test")] as Set)
            x == y
    }
}
