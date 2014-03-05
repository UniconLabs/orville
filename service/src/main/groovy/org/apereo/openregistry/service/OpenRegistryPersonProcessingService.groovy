package org.apereo.openregistry.service

import org.apereo.openregistry.model.OpenRegistryPerson
import org.apereo.openregistry.model.SystemOfRecordPerson

/**
 *
 * Main facade API for processing incoming {@link org.apereo.openregistry.model.SystemOfRecordPerson}s and producing
 * canonical {@link org.apereo.openregistry.model.OpenRegistryPerson}s.
 *
 * Implementors might utilize smaller, finer-grained components for organizing processing steps into a processing pipeline.
 * Or this API could be used as <i>integration gateway</i> component to kick off the processing chain composed of the smaller components
 * via some integration frameworks e.g. Apache Camel, Spring Integration, Spring XD, etc.
 *
 * This API does not assume that implementation will return an instance that has been persisteted into some persistnce store.
 * A separate persistence component should take care of all the persistence details.
 */
public interface OpenRegistryPersonProcessingService {

    OpenRegistryPerson processIncoming(SystemOfRecordPerson systemOfRecordPerson)
}