package org.apereo.openregistry.service

import org.apereo.openregistry.model.OpenRegistryPerson
import org.apereo.openregistry.model.SystemOfRecordPerson

/**
 *
 * A context carrying state relevant to processing step
 */
class OpenRegistryProcessorContext {

    Object request

    Object outcome

    SystemOfRecordPerson systemOfRecordPerson

    OpenRegistryPerson openRegistryPerson
}
