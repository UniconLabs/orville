package org.apereo.openregistry.service.notification

import org.apereo.openregistry.model.Person

/**
 * Notification service API for sending push notifications to outside resources about registry's entities (people) changes.
 *
 * This service implementation should be wired into the appropriate processor which should be then place into the appropriate spot
 * in the processing pipeline (after the persistence processor)
 *
 * @author Dmitriy Kopylenko
 * @author Unicon, inc
 */
public interface NotificationService {

    void pushPersonCreatedOrChangedNotification(Person person)
}