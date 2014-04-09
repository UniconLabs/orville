package org.apereo

import org.apereo.openregistry.model.Name
import org.apereo.openregistry.model.NameIdentifier
import org.apereo.openregistry.model.Person
import org.apereo.openregistry.model.SystemOfRecord
import org.apereo.openregistry.model.TokenIdentifier
import org.apereo.openregistry.model.Type
import org.h2.jdbcx.JdbcDataSource
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

import javax.sql.DataSource

/*
@EnableAutoConfiguration
@ComponentScan
@Configuration
 */
class SimpleApplication implements CommandLineRunner {
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
            wallet.add(new NameIdentifier(systemOfRecord: s, name: new Name(givenName: "Jj"), type: nameType))
            wallet.add(new TokenIdentifier(systemOfRecord: s, token: "this is a test", type: type))
            return it
        }.save()

        Person.withTransaction {
            Person.findAll().each { person ->
                println "person: ${person}"
                person.wallet.each {
                    println "\there: ${it}: ${it.class}: ${it instanceof TokenIdentifier ? it.token : ""}"
                }
            }
        }
        System.exit(0)
    }
}

def app = new SpringApplication(SimpleApplication)
app.run(args)