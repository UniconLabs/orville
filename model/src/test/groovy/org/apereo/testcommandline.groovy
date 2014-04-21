package org.apereo

import org.apereo.openregistry.model.Name
import org.apereo.openregistry.model.NameIdentifier
import org.apereo.openregistry.model.Person
import org.apereo.openregistry.model.SystemOfRecord
import org.apereo.openregistry.model.TokenIdentifier
import org.apereo.openregistry.model.Type
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.orm.hibernate.cfg.HibernateUtils
import org.h2.jdbcx.JdbcDataSource
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

import javax.sql.DataSource

@EnableAutoConfiguration
@ComponentScan
@Configuration
class SimpleApplication implements CommandLineRunner {
    @Autowired
    ApplicationContext applicationContext

    @Autowired
    GrailsApplication grailsApplication

    /*
    @Autowired
    SessionFactory sessionFactory
     */

    @Bean
    DataSource dataSource() {
        new JdbcDataSource().with {
            it.URL = "jdbc:h2:/tmp/orville"
            user = "sa"
            password = "sa"
            return it
        }
    }

    @Override
    void run(String... args) throws Exception {
        println "here we are"

        HibernateUtils.enhanceSessionFactory(applicationContext.getBean(SessionFactory), applicationContext.getBean(GrailsApplication), applicationContext)

        def s = SystemOfRecord.findByCode("OR")
        if (!s) {
            s = new SystemOfRecord(code: "OR").save()
        }

        def type = Type.findByTargetAndValue(TokenIdentifier, 'test')
        if (!type) {
            type = new Type(target: TokenIdentifier, value: 'test').save()
        }

        def nameType = Type.findByTargetAndValue(NameIdentifier, "preferred")
        if (!nameType) {
            nameType = new Type(target: NameIdentifier, value: "preferred").save()
        }
        new Person().with {
            it.addToWallet(new NameIdentifier(systemOfRecord: s, name: new Name(givenName: "Jj"), type: nameType))
            it.addToWallet(new TokenIdentifier(systemOfRecord: s, token: "this is a test", type: type))
            return it
        }.save()

        Person.withTransaction {
            Person.findAll().each { person ->
                println "person: ${person}"
                person.wallet.each {
                    println "\there: ${it}: ${it.class}: ${it instanceof TokenIdentifier ? it.token : ""}"
                }
                person.nameIdentifiers.each {
                    println "\tname: ${it}"
                }
            }
        }

        def test = new TokenIdentifier(systemOfRecord: s, token: "this is another test", type: nameType).save(failOnError: true)
        System.exit(0)
    }
}

def app = new SpringApplication(SimpleApplication)
app.run(args)