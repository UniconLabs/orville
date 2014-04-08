package org.apereo.openregistry.model

import grails.persistence.Entity

@Entity
class Type {
    Class target
    String value
}
