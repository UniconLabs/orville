package org.apereo.openregistry.springboot

import org.apereo.openregistry.service.CompositeOpenRegistryProcessor
import org.apereo.openregistry.service.MockOutcomeProcessor
import org.apereo.openregistry.service.OpenRegistryProcessor
import org.apereo.openregistry.service.identification.IdentificationProcessor
import org.apereo.openregistry.service.reconciliation.ReconciliationProcessor
import org.apereo.openregistry.service.standardization.StandardizationProcessor
import org.h2.jdbcx.JdbcDataSource
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.context.web.SpringBootServletInitializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

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
                        new IdentificationProcessor(),
                        new MockOutcomeProcessor.AddPersonMockOutcome_200()] as LinkedHashSet

        new CompositeOpenRegistryProcessor(pipeline)
    }

    @Bean
    DataSource dataSource() {
        //TODO: externalize the datasource config somehow to make it a deployment config concern!!!
        new JdbcDataSource().with {
            it.URL = "jdbc:h2:/tmp/orville"
            user = "sa"
            password = "sa"
            return it
        }
    }
}
