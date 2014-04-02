package org.apereo.openregistry.springboot

import org.apereo.openregistry.service.CompositeOpenRegistryProcessor
import org.apereo.openregistry.service.MockOutcomeProcessor
import org.apereo.openregistry.service.OpenRegistryProcessor
import org.apereo.openregistry.service.calculation.CalculationProcessor
import org.apereo.openregistry.service.election.ElectionProcessor
import org.apereo.openregistry.service.identification.OpenRegistryIdentificationProcessor
import org.apereo.openregistry.service.identification.SystemOfRecordIdentificationProcessor
import org.apereo.openregistry.service.reconciliation.ReconciliationProcessor
import org.apereo.openregistry.service.standardization.StandardizationProcessor
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.context.web.SpringBootServletInitializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

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
                        new SystemOfRecordIdentificationProcessor(),
                        new OpenRegistryIdentificationProcessor(),
                        new MockOutcomeProcessor.AddPersonMockOutcome_201()] as LinkedHashSet

        new CompositeOpenRegistryProcessor(pipeline)
    }
}
