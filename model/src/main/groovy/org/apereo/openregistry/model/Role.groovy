package org.apereo.openregistry.model

/**
 * Class representing a role.
 */
class Role {
    Entity sponsor
    String title
    int percentTime
    Affiliation affiliation
    Termination termination
    Map<String, String> extendedAttributes

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
