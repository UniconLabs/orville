import org.apereo.openregistry.model.Identifier
import org.apereo.openregistry.model.Name
import org.apereo.openregistry.model.Person
import org.apereo.openregistry.model.SystemOfRecord
import org.apereo.openregistry.model.NameIdentifier
import org.h2.jdbcx.JdbcDataSource
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan

import javax.sql.DataSource

@EnableAutoConfiguration
@ComponentScan
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
        def user = new Person().with {
            wallet.add(new NameIdentifier(systemOfRecord: s, info: new Name(givenName: "Jj")))
            wallet.add(new Identifier(systemOfRecord: s, info: "this is a test"))
            return it
        }.save()

        Person.withTransaction {
            Person.findAll().each { person ->
                person.wallet.each {
                    println "here: ${it}: ${it.class}: ${it.info}"
                }
            }
        }
        System.exit(0)
    }
}

def app = new SpringApplication(SimpleApplication)
app.run(args)