package org.example.userflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.example.userflow", "org.example.userdomain"})
public class UserFlowApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserFlowApplication.class, args);
    }

}
