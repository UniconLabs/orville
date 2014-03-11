import grails.persistence.Entity
import org.apereo.openregistry.model.EmailAddress
import org.apereo.openregistry.model.Name
import org.apereo.openregistry.model.impl.gorm.GormPerson
import org.hibernate.validator.constraints.Email
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.apereo.openregistry.model.Person.Gender

import static org.springframework.web.bind.annotation.RequestMethod.POST

@Entity
class Item {
    String name
}

@Controller
@EnableAutoConfiguration
@ComponentScan
class TestController {
    @RequestMapping(value = "/person/add", method = POST)
    ResponseEntity addPerson(String firstName) {
        GormPerson.withTransaction {
            def p = new GormPerson(
                    dateOfBirth: new Date(),
                    gender: Gender.NONE,
                    names: [] as Set<Name>,
                    emailAddresses: [] as Set<EmailAddress>
            )
            def x = p.save()
            new ResponseEntity(p ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST)
        }
    }
}

def app = new SpringApplication(TestController)
app.run(args)