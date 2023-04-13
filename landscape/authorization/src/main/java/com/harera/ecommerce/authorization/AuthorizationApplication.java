package com.harera.ecommerce.authorization;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com.harera.ecommerce.*" })
public class AuthorizationApplication {

    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(AuthorizationApplication.class,
                        args);
    }
}
