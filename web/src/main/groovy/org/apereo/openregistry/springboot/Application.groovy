package org.apereo.openregistry.springboot

import org.apereo.openregistry.model.SystemOfRecordPerson
import org.apereo.openregistry.service.SystemOfRecordPersonFactory
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

    /**
     * This is Spring's Java config method. The bean with name 'systemOfRecordPersonFactory'
     * will be created in the parent app ctx and will be a subject for autowiring - will be injected into those beans
     * that express dependency on it via @Autowired or @Inject
     */
    @Bean
    SystemOfRecordPersonFactory systemOfRecordPersonFactory() {
        //Just for initial Boot's testing at the beginning of
        //development. Will be replaced with real implementation later...
        new SystemOfRecordPersonFactory() {

            @Override
            SystemOfRecordPerson createFrom(Map<Object, Object> systemOfRecordPersonData) {
                new SystemOfRecordPerson(systemOfRecordPersonData)
            }

            @Override
            String toString() {
                "Mock SystemOfRecordPersonFactory"
            }
        }
    }
}
