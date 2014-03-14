package org.apereo.openregistry.service

/**
 *
 * A main abstraction for Open Registry processing step
 */
public interface OpenRegistryProcessor {

    OpenRegistryProcessorContext process(OpenRegistryProcessorContext processorContext)
}