package org.apereo.openregistry.service.validation

import groovy.transform.Immutable

/**
 *
 * Holds results of a validation service call
 */
@Immutable
class PersonValidationResult {

   boolean success

   Set<String> messages
}
