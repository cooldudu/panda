package com.wms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.reactive.config.EnableWebFlux;


@SpringBootApplication
public class Application{
    public static void main(String[] args){
        SpringApplication.run(Application.class);
    }
}
