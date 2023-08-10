package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages = "org.example")
public class AppMVC {
    public static void main(String[] args) {
	ConfigurableApplicationContext run = SpringApplication.run(AppMVC.class, args);
    }
}