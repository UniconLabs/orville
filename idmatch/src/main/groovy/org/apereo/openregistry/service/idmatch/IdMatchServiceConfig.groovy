package org.apereo.openregistry.service.idmatch

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

import javax.annotation.PostConstruct

@Component
class IdMatchServiceConfig {
    @Value('${idMatch.baseUrl}')
    String baseUrl

    @Value('${idMatch.username}')
    String username

    @Value('${idMatch.password}')
    String password

    @Value('${idMatch.version}')
    String version

    @Value('${idMatch.preempt}')
    boolean preempt

    @PostConstruct
    void validate() {
        assert baseUrl.endsWith('/') : "baseUrl must end in '/'"
    }
}
