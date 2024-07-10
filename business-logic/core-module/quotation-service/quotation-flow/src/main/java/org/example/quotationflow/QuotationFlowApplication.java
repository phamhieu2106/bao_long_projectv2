package org.example.quotationflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.example.quotationdomain", "org.example.quotationflow"})
public class QuotationFlowApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuotationFlowApplication.class, args);
    }

}
