package org.apereo.openregistry.springboot

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.context.web.SpringBootServletInitializer
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
        //def ar = ['spring.config.location=/etc/openregistry/config/openregistry.properties']
        SpringApplication.run(applicationClass, args)
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        application.sources(applicationClass)
    }
}
