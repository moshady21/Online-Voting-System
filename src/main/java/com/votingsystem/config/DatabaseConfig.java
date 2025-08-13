package com.votingsystem.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.votingsystem.repository")
@EnableTransactionManagement
public class DatabaseConfig {
    // JPA configuration handled by Spring Boot auto-configuration
    // Custom configurations can be added here if needed
}