package com.dohdonglog;

import com.dohdonglog.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(AppConfig.class)
@SpringBootApplication
public class DohdonglogApplication {

    public static void main(String[] args) {
        SpringApplication.run(DohdonglogApplication.class, args);
    }

}
