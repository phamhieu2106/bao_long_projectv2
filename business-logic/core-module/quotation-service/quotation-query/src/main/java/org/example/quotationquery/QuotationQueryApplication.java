package org.example.quotationquery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.example.quotationdomain", "org.example.quotationquery"})
public class QuotationQueryApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuotationQueryApplication.class, args);
    }

}
