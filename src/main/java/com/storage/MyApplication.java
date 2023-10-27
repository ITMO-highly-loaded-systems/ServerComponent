package com.storage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.storage")
public class MyApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class);
    }
}
