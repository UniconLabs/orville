package org.apereo.openregistry

import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.orm.hibernate.cfg.HibernateUtils
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

import javax.annotation.PostConstruct

@EnableAutoConfiguration
@Configuration
@ComponentScan(basePackages = ['org.apereo.openregistry'])
class TestApplication {
    @Autowired
    ApplicationContext applicationContext

    @PostConstruct
    void setup() {
        HibernateUtils.enhanceSessionFactory(applicationContext.getBean(SessionFactory), applicationContext.getBean(GrailsApplication), applicationContext)
    }
}