package org.example.quotationcommand;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.example.quotationdomain", "org.example.quotationcommand"})
@EnableFeignClients
public class QuotationCommandApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuotationCommandApplication.class, args);
    }

}
