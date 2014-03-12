package org.apereo.openregistry.model

import groovy.transform.InheritConstructors

/**
 *
 * To indicate that no persons exist for a given input
 */
@InheritConstructors
class SystemOfRecordPersonNotFoundException extends RuntimeException {
}
