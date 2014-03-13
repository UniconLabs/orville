package org.apereo.openregistry.model

@grails.persistence.Entity
class Person extends Entity {
    Date dateOfBirth
    Gender gender
    Set<EmailAddress> emailAddresses
    Set<Name> names
    Set<Role> roles

    /**
     * Enum representing genders. This list of genders was gathered from multiple sources including Wikipedia(R) and
     * Facebook(R).
     */
    static enum Gender {
        AGENDER, ANDROGYNE, ANDROGYNOUS, BIGENDER, NONBINARY, MALETOFEMALE, FEMALETOMALE, TRANS, TRANSFEMALE, TRANSMALE,
        TRANSMAN, TRANSPERSON, CIS, CISFEMALE, CISMALE, CISWOMAN, CISMAN, CISGENDER, CISGENDERMALE, CISGENDERFEMALE,
        CISGENDERMAN, CISGENDERWOMAN, GENDERFLUID, GENDERNONCONFORMING, GENDERQUESTIONING, GENDERVARIANT, GENDERQUEER,
        NONE, NEITHER, NEUTROIS, FTM, MTF, TRANSGENDERFEMALE, TRANSGENDERMALE, OTHER, INTERSEX, TRANSSEXUAL, TRANSSEXUALMAN,
        TRANSSEXUALWOMAN, TRANSSEXUALPERSON, TRANSSEXUALMALE, TRANSSEXUALFEMALE, TRANSMASCULINE, TWOSPIRIT, PANGENDER,
        UNDISCLOSED
    }
}