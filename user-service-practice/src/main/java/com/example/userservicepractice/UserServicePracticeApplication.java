package com.example.userservicepractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class UserServicePracticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServicePracticeApplication.class, args);
    }

}
