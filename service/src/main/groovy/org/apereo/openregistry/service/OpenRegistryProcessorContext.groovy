package org.apereo.openregistry.service

import groovy.transform.ToString
import org.apereo.openregistry.model.OpenRegistryPerson
import org.apereo.openregistry.model.SystemOfRecordPerson
import org.apereo.openregistry.model.request.OpenRegistryProcessingRequest

/**
 *
 * A context carrying state relevant to processing step
 */
@ToString
class OpenRegistryProcessorContext {

    OpenRegistryProcessingRequest request

    /**
     * Encapsulates the state pertaining to the outcome of open registry processing request.
     * <strong>
     *     REQUIRED CONTRACT: body -> (Object)a response body; isFrozen -> (Boolean)indicates if the outcome is final and no
     *     further processing is required
     * </strong>
     *
     * Any other free form entries are allowed which could be interpreted by specific clients that know about their structure
     */
    Map<String, Object> outcome = [body: null, isFrozen: false]

    SystemOfRecordPerson systemOfRecordPerson

    OpenRegistryPerson openRegistryPerson
}
