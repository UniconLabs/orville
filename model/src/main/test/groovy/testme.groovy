import grails.persistence.Entity
import org.h2.jdbcx.JdbcDataSource
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

import javax.sql.DataSource

import static org.springframework.web.bind.annotation.RequestMethod.POST
@Controller
@EnableAutoConfiguration
@ComponentScan
class TestController {
    @Bean
    DataSource dataSource() {
        new JdbcDataSource().with {
            it.URL = "jdbc:h2:/tmp/orville"
            user = "sa"
            password="sa"
            return it
        }
    }
    @RequestMapping(value = "/person/add", method = POST)
    ResponseEntity addPerson(String firstName) {
        return new ResponseEntity(HttpStatus.OK)
    }
}

def app = new SpringApplication(TestController)
app.run(args)