package org.example.customerflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.example.customerdomain", "org.example.customerflow"})
public class CustomerFlowApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerFlowApplication.class, args);
    }

}
