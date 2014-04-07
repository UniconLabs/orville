package org.apereo.openregistry.service.standardization

import org.apereo.openregistry.service.OpenRegistryProcessorContext

/**
 * Handles mapping input into an object graph. Typical implementations will take input from
 * <code>openRegistryProcessorContext.request</code> and map it to
 * <code>openregistryProcessorContext.systemoOfRecordPerson</code>
 */
public interface StandardizationService {
    OpenRegistryProcessorContext standardize(OpenRegistryProcessorContext openRegistryProcessorContext)
}