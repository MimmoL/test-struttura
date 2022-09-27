package com.ibm.intest;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


/*
@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories("com.ibm.intest.repositories")

*/
@SpringBootApplication
public class IntestApplication {
    public static void main(String[] args) {
        SpringApplication.run(IntestApplication.class, args);
    }
}