package org.apereo.openregistry.service.standardization

import org.apereo.openregistry.service.OpenRegistryProcessorContext

public interface StandardizationService {
    OpenRegistryProcessorContext standardize(OpenRegistryProcessorContext openRegistryProcessorContext)
}