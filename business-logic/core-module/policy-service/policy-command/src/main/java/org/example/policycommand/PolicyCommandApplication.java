package org.example.policycommand;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.example.policycommand", "org.example.policydomain"})
@EnableFeignClients
public class PolicyCommandApplication {

    public static void main(String[] args) {
        SpringApplication.run(PolicyCommandApplication.class, args);
    }

}
