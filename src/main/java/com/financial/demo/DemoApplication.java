package com.financial.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
    public static final String API_START_URL = "/api/v1/convertor";

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
