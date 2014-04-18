package org.apereo.openregistry

import com.jolbox.bonecp.BoneCPDataSource
import org.apereo.openregistry.model.*
import org.apereo.openregistry.service.CompositeOpenRegistryProcessor
import org.apereo.openregistry.service.MockOutcomeProcessor
import org.apereo.openregistry.service.OpenRegistryProcessor
import org.apereo.openregistry.service.identification.IdentificationProcessor
import org.apereo.openregistry.service.identification.internal.RandomUUIDTokenGeneratorStrategy
import org.apereo.openregistry.service.identification.internal.SystemOfRecordTokenIdentifierService
import org.apereo.openregistry.service.idmatch.IdMatchProcessor
import org.apereo.openregistry.service.idmatch.IdMatchService
import org.apereo.openregistry.service.persistence.PersistenceProcessor
import org.apereo.openregistry.service.standardization.SimpleStandardizationService
import org.apereo.openregistry.service.standardization.StandardizationProcessor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.context.web.SpringBootServletInitializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

import javax.annotation.PostConstruct
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
    String databaseDriver

    @Value('${database.url}')
    String databaseUrl

    @Value('${database.username}')
    String databaseUsername

    @Value('${database.password}')
    String databasePassword

    @Autowired
    IdMatchService idMatchService

    public static void main(String[] args) {
        def application = new SpringApplication(applicationClass)
        application.run(args)
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        application.sources(applicationClass, BootStrap)
    }

    @Bean
    OpenRegistryProcessor defaultOpenRegistryProcessingEngine() {
        def pipeline = [new StandardizationProcessor(standardizationService: new SimpleStandardizationService()),
                        //new ReconciliationProcessor(),
                        new IdentificationProcessor(new SystemOfRecordTokenIdentifierService('', new RandomUUIDTokenGeneratorStrategy()), 'guest'),
                        new IdMatchProcessor(idMatchService: idMatchService),
                        new PersistenceProcessor(),
                        new MockOutcomeProcessor.AddPersonMockOutcome_201()] as LinkedHashSet

        new CompositeOpenRegistryProcessor(pipeline)
    }

    /*
    @Bean(destroyMethod = 'close')
    DataSource dataSource() {
        new BoneCPDataSource().with {
            it.driverClass = this.databaseDriver
            it.jdbcUrl = this.databaseUrl
            it.username = this.databaseUsername
            it.password = this.databasePassword
            return it
        }
    }
     */

    @Configuration
    static class BootStrap {
        @PostConstruct
        def bootstrap() {
            // set up systems of record
            ['test', 'guest', 'idmatch'].each {
                if (!SystemOfRecord.findByCodeAndActive(it, true)) {
                    new SystemOfRecord(code: it, active: true).save()
                }
            }

            // set up types
            // TODO: maps are easy, but you could have collisions
            [
                    "official": NameIdentifier,
                    "personal": EmailAddressIdentifier,
                    "network": TokenIdentifier,
                    "guest": TokenIdentifier,
                    "referenceid": TokenIdentifier
            ].each { value, target ->
                if (!Type.findByTargetAndValue(target, value)) {
                    new Type(target: target, value: value).save()
                }
            }
        }
    }
}
