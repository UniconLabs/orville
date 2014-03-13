package org.apereo.openregistry.model

import groovy.transform.Immutable

/**
 *
 * Represent activation key
 */
@Immutable
class ActivationKey {

    String value

    Date validThrough

    def getValidThroughAsString() {
        this.validThrough.format("yyyy-MM-dd'T'HH:mm:ss'Z'", TimeZone.getTimeZone('UTC'))
    }
}
