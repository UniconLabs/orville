package org.apereo.openregistry.service.activation

import grails.persistence.Entity
import groovy.transform.EqualsAndHashCode
import org.apereo.openregistry.model.Identifier

/**
 *
 * Entity which models activation keys abstraction.
 */
@EqualsAndHashCode
@Entity
class ActivationKey {

    String id

    Identifier identifier

    Date validThroughDate

    boolean used = false

    static constraints = {
        identifier unique: true
    }

    static mapping = {
        id generator: 'uuid'
    }

    /**
     * Business method that encapsulates key validity check logic
     */
    def isValid() {
        !this.used && this.validThroughDate >= new Date()
    }

    def getValidThroughDateAsUtcFormattedString() {
        this.validThroughDate.format("yyyy-MM-dd'T'HH:mm:ss'Z'", TimeZone.getTimeZone('UTC'))
    }
}
