package com.tomasky.departure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.tomasky.departure")
@SpringBootApplication
public class DepartureWebApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DepartureWebApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(DepartureWebApplication.class, args);
    }

}
