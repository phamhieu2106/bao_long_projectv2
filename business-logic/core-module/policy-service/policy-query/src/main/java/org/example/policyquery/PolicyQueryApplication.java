package org.example.policyquery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.example.policydomain", "org.example.policyquery"})
public class PolicyQueryApplication {

    public static void main(String[] args) {
        SpringApplication.run(PolicyQueryApplication.class, args);
    }

}
