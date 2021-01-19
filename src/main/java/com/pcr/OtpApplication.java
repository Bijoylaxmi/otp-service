package com.pcr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.pcr"})
public class OtpApplication {
    public static void main(String[] args) {
        SpringApplication.run(OtpApplication.class, args);

   }
    
}