package org.apereo.openregistry.model

/**
 * Class representing a role.
 */
@grails.persistence.Entity
class Role {
    Entity sponsor
    String title
    int percentTime = 100
    Affiliation affiliation
    Termination termination

    static constraints = {
        termination nullable: true
    }

    @grails.persistence.Entity
    static class Affiliation {
        Date date
        AffiliationType type

        static enum AffiliationType {}
    }

    @grails.persistence.Entity
    static class Termination {
        Date date
        TerminationType type

        static enum TerminationType {}
    }
}
