package org.apereo.openregistry.model

import spock.lang.Specification

class NameSpecification extends Specification {
    def "simple equals test"() {
        expect:
        new Name() == new Name()
    }
}
