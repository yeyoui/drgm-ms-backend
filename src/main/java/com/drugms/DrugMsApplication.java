package com.drugms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class DrugMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DrugMsApplication.class, args);
    }

}
