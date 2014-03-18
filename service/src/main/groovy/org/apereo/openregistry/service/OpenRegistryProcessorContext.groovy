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

    Object outcome

    SystemOfRecordPerson systemOfRecordPerson

    OpenRegistryPerson openRegistryPerson
}
