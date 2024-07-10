package org.example.customercommand;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.example.customerdomain", "org.example.customercommand"})
@EnableFeignClients
public class CustomerCommandApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerCommandApplication.class, args);
    }

}
