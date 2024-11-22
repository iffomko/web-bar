package org.iffomko.server.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "bar.auth")
@Getter
@Setter
public class AuthAppConfig {
    private int strength;
}
