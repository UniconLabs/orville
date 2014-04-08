package org.apereo.openregistry.springboot

import org.apereo.openregistry.service.CompositeOpenRegistryProcessor
import org.apereo.openregistry.service.MockOutcomeProcessor
import org.apereo.openregistry.service.OpenRegistryProcessor
import org.apereo.openregistry.service.identification.IdentificationProcessor
import org.apereo.openregistry.service.identification.internal.RandomUUIDTokenGeneratorStrategy
import org.apereo.openregistry.service.identification.internal.SystemOfRecordTokenIdentifierService
import org.apereo.openregistry.service.reconciliation.ReconciliationProcessor
import org.apereo.openregistry.service.standardization.StandardizationProcessor
import org.h2.server.web.WebServlet
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.context.embedded.ServletRegistrationBean
import org.springframework.boot.context.web.SpringBootServletInitializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DriverManagerDataSource

import javax.sql.DataSource

/**
 *
 * Spring Boot main Application
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = 'org.apereo.openregistry')
class Application extends SpringBootServletInitializer {

    private static applicationClass = Application

    @Value('${database.driver}')
    private String databaseDriver

    @Value('${database.url}')
    private String databaseUrl

    @Value('${database.username}')
    private String databaseUsername

    @Value('${database.password}')
    private String databasePassword

    public static void main(String[] args) {
        SpringApplication.run(applicationClass, args)
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        application.sources(applicationClass)
    }

    @Bean
    OpenRegistryProcessor defaultOpenRegistryProcessingEngine() {
        //TODO: these processors are not implemented yet. Also what is the correct order of them in the pipeline???
        def pipeline = [new StandardizationProcessor(),
                        new ReconciliationProcessor(),
                        new IdentificationProcessor(new SystemOfRecordTokenIdentifierService('guest-', new RandomUUIDTokenGeneratorStrategy()), 'guest'),
                        new MockOutcomeProcessor.AddPersonMockOutcome_201()] as LinkedHashSet

        new CompositeOpenRegistryProcessor(pipeline)
    }

    @Bean
    DataSource dataSource() {
        new DriverManagerDataSource(driverClassName: databaseDriver,
                url: databaseUrl,
                username: databaseUsername,
                password: databasePassword)
    }
}
