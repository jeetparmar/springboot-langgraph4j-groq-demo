package com.example.groqgraph;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class GroqGraphApplication {
    public static void main(String[] args) {
        SpringApplication.run(GroqGraphApplication.class, args);
    }
}
