package org.apereo.openregistry

import org.apereo.openregistry.model.*
import org.apereo.openregistry.service.CompositeOpenRegistryProcessor
import org.apereo.openregistry.service.OpenRegistryProcessor
import org.apereo.openregistry.service.identification.IdentificationProcessor
import org.apereo.openregistry.service.identification.internal.RandomUUIDTokenGeneratorStrategy
import org.apereo.openregistry.service.identification.internal.SystemOfRecordTokenIdentifierService
import org.apereo.openregistry.service.idmatch.IdMatchProcessor
import org.apereo.openregistry.service.idmatch.IdMatchService
import org.apereo.openregistry.service.persistence.PersistenceProcessor
import org.apereo.openregistry.service.standardization.SimpleStandardizationService
import org.apereo.openregistry.service.standardization.StandardizationProcessor
import org.apereo.openregistry.service.notification.internal.HttpPutNotificationService
import org.apereo.openregistry.service.notification.NotificationProcessor
import org.apereo.openregistry.service.notification.NotificationServiceConfigProperties
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.orm.hibernate.cfg.HibernateUtils
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.context.web.SpringBootServletInitializer
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

import javax.annotation.PostConstruct

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
        def application = new SpringApplication(applicationClass)
        application.run(args)
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        application.sources(applicationClass, BootStrap)
    }

    @Bean
    OpenRegistryProcessor defaultOpenRegistryProcessingEngine(IdMatchService idMatchService,
                                                              NotificationServiceConfigProperties notificationServiceConfigProperties) {


        def pipeline = [new StandardizationProcessor(standardizationService: new SimpleStandardizationService()),
                        //new ReconciliationProcessor(),
                        new IdentificationProcessor(new SystemOfRecordTokenIdentifierService('', new RandomUUIDTokenGeneratorStrategy()), 'guest'),
                        new IdMatchProcessor(idMatchService: idMatchService),
                        new PersistenceProcessor(),
                        new NotificationProcessor(new HttpPutNotificationService(notificationServiceConfigProperties))] as LinkedHashSet

        new CompositeOpenRegistryProcessor(pipeline)
    }

    @Configuration
    //TODO: find a better place for this bootstrap code
    static class BootStrap {
        @Autowired
        ApplicationContext applicationContext

        @Autowired
        SessionFactory sessionFactory

        @Autowired
        GrailsApplication grailsApplication

        @PostConstruct
        def bootstrap() {
            HibernateUtils.enhanceSessionFactory(sessionFactory, grailsApplication, applicationContext)
            // set up systems of record
            ['test', 'guest', 'idmatch'].each {
                if (!SystemOfRecord.findByCodeAndActive(it, true)) {
                    new SystemOfRecord(code: it, active: true).save()
                }
            }

            // set up types
            // TODO: maps are easy, but you could have collisions
            [
                    "official"   : NameIdentifier,
                    "personal"   : EmailAddressIdentifier,
                    "network"    : TokenIdentifier,
                    "guest"      : TokenIdentifier,
                    "referenceid": TokenIdentifier,
                    "add"        : Baggage,
                    "delete"     : Baggage,
                    "update"     : Baggage
            ].each { value, target ->
                if (!Type.findByTargetAndValue(target, value)) {
                    new Type(target: target, value: value).save()
                }
            }
        }
    }
}
