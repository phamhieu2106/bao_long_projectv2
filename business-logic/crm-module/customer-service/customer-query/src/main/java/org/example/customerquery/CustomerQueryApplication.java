package org.example.customerquery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.example.customerdomain", "org.example.customerquery"})
public class CustomerQueryApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerQueryApplication.class, args);
    }

}
