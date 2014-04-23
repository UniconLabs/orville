package org.apereo.openregistry.service.notification

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

/**
 * Holder for externalized config properties for notification service
 *
 * @author Dmitriy Kopylenko
 * @author Unicon inc.
 */
@Component
class NotificationServiceConfigProperties {

    //TODO: actually get rid of the default values from @Value annotation and require them to be set externally

    @Value('${notification.enity.baseUri:/v1/people/enterprise}')
    String notificationEntityBaseUri

    @Value('${notification.resource.host:http://example.com}')
    String notificationResourceHost

    @Value('${notification.resource.baseUri:v1/events/registry}')
    String notificationResourceUri

    @Value('${notification.resource.username:test')
    String notificationReousrceUsername

    @Value('${notification.resource.password:password')
    String notificationResourcePassword
}
