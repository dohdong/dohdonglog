package com.dohdonglog.config;

import java.util.Base64;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Data
@ConfigurationProperties(prefix = "dohdongman")
public class AppConfig {

//    public static final String KEY = "/TJQPaA3e7TuJmTQ7zekUqqr+eEqnS9f+jGjFvkA7to=";

    private byte[] jwtKey;

    public void setJwtKey(String jwtKey) {
        this.jwtKey = Base64.getDecoder().decode(jwtKey);
    }

    public byte[] getJwtKey() {
        return jwtKey;
    }
}
