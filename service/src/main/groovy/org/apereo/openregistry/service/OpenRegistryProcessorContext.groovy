package org.apereo.openregistry.service

import groovy.transform.ToString
import org.apereo.openregistry.model.Baggage
import org.apereo.openregistry.model.Person

/**
 *
 * A context carrying state relevant to processing step
 */
@ToString
class OpenRegistryProcessorContext {

    Baggage request

    /**
     * Encapsulates the state pertaining to the outcome of open registry processing request.
     * <strong>
     *     REQUIRED CONTRACT: body -> (Object)a response body
     *                        isFrozen -> (Boolean)indicates if the outcome is final and no further processing is required
     * </strong>
     *
     * Any other free form entries are allowed which could be interpreted by specific clients that know about their structure
     */

    Map<String, Object> outcome = [body: null, isFrozen: false]

    Person person
}
