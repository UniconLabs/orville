package org.apereo.openregistry.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Spring Configuration class for Embedded Tomcat SSL support
 *
 * @author Dmitriy Kopylenko
 * @Unicon inc
 */
@Configuration
class TomcatSslConfiguration {

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer(@Value('${ssl.keystore.file}') String keystoreFile,
                                                                  @Value('${ssl.keystore.password}') String keystorePassword,
                                                                  @Value('${ssl.keystore.type}') String keystoreType,
                                                                  @Value('${ssl.keystore.alias}') String keystoreAlias) throws FileNotFoundException {
        return {
            (it as TomcatEmbeddedServletContainerFactory).addConnectorCustomizers({
                it.setSecure(true);
                it.setScheme('https');
                it.setAttribute('keystoreFile', keystoreFile);
                it.setAttribute('keystorePass', keystorePassword);
                it.setAttribute('keystoreType', keystoreType);
                it.setAttribute('keyAlias', keystoreAlias);
                it.setAttribute('clientAuth', false);
                it.setAttribute('sslProtocol', 'TLS');
                it.setAttribute('SSLEnabled', true);
            } as TomcatConnectorCustomizer)
        } as EmbeddedServletContainerCustomizer
    }
}
