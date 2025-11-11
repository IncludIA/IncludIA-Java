package com.fiap.gs2025.IncludIA_Java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching; // 1. IMPORTAR

@SpringBootApplication
@EnableCaching
public class IncludIaJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(IncludIaJavaApplication.class, args);
    }

}