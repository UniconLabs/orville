package org.apereo.openregistry.service.notification.internal

import groovy.util.logging.Slf4j
import groovyx.net.http.ContentType
import groovyx.net.http.RESTClient
import org.apereo.openregistry.model.Person
import org.apereo.openregistry.service.notification.NotificationService
import org.apereo.openregistry.service.notification.NotificationServiceConfigProperties

/**
 *
 * NotificationService implementation that PUTs a predetermined JSON format representing the notification to a configured HTTP resource
 *
 * @author Dmitriy Kopylenko
 * @author Unicon , inc
 */
@Slf4j
class HttpPutNotificationService implements NotificationService {

    private final NotificationServiceConfigProperties configProperties

    private final RESTClient restClient

    HttpPutNotificationService(NotificationServiceConfigProperties configProperties) {
        this.configProperties = configProperties
        this.restClient = new RESTClient("${this.configProperties.notificationResourceHost}/".toString())
        this.restClient.auth.basic(this.configProperties.notificationReousrceUsername, this.configProperties.notificationResourcePassword)
    }

    @Override
    void pushPersonCreatedOrChangedNotification(Person person) {
        def serialNumber = person.highestBaggageId
        def notificationResourceUri =
                "${this.configProperties.notificationResourceHost}/${this.configProperties.notificationResourceUri}/$serialNumber"

        def notificationRequestBody = [serialNumber: serialNumber,
                                       entity      : "${this.configProperties.notificationEntityBaseUri}/$person.id".toString(),
                                       timestamp   : person.lastUpdatedDateAsUtcFormattedString]

        log.debug("Trying to send the following notification content: $notificationRequestBody to the following resource URI: $notificationResourceUri")
        try {
            this.restClient.put(
                    path: "${this.configProperties.notificationResourceUri}/$serialNumber".toString(),
                    body: notificationRequestBody,
                    requestContentType: ContentType.JSON)
        }
        catch (Throwable e) {
            //TODO: how to best handle the exception? Currently it just logs at the WARN level and moves on. Is it OK?
            log.warn("Failed to send the notification request. The original exception is: ", e)
        }
    }
}


