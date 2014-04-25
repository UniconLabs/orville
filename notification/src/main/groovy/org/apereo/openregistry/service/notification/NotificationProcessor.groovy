package org.apereo.openregistry.service.notification

import groovy.transform.EqualsAndHashCode
import groovy.util.logging.Slf4j
import org.apereo.openregistry.service.OpenRegistryProcessor
import org.apereo.openregistry.service.OpenRegistryProcessorContext

/**
 * Processor responsible for sending push notifications via notification service
 *
 * @author Dmitriy Kopylenko
 * @author Unicon inc.
 */
@EqualsAndHashCode
@Slf4j
class NotificationProcessor implements OpenRegistryProcessor {

    private final NotificationService notificationService

    NotificationProcessor(NotificationService notificationService) {
        this.notificationService = notificationService
    }

    @Override
    OpenRegistryProcessorContext process(OpenRegistryProcessorContext processorContext) {
        log.info("Starting 'notification' processing phase with [$processorContext]")
        //TODO: do some checks to make sure that the person entity is actually persisted???
        this.notificationService.pushPersonCreatedOrChangedNotification(processorContext.person)
        return processorContext
    }
}
