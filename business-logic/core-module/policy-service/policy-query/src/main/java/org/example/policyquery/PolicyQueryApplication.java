package org.example.policyquery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"org.example.policydomain", "org.example.policyquery"})
@EnableFeignClients
@EnableElasticsearchRepositories("org.example.policydomain.repository.view")
public class PolicyQueryApplication {

    public static void main(String[] args) {
        SpringApplication.run(PolicyQueryApplication.class, args);
    }

}
