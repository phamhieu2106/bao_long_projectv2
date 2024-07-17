package org.example.policyflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.example.policydomain", "org.example.policyflow"})
public class PolicyFlowApplication {

    public static void main(String[] args) {
        SpringApplication.run(PolicyFlowApplication.class, args);
    }

}
