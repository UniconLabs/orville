package org.apereo.openregistry.model

/**
 * Class representing a role.
 */
@grails.persistence.Entity
class Role {
    Entity sponsor
    String title
    int percentTime
    Affiliation affiliation
    Termination termination

    static class Affiliation {
        Date date
        AffiliationType type

        static enum AffiliationType {}
    }

    static class Termination {
        Date date
        TerminationType type

        static enum TerminationType {}
    }
}
