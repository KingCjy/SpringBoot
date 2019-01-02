package com.kingcjy.main;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = "com.kingcjy.main")
@EnableJpaRepositories(basePackages = "com.kingcjy.main")
public class TestConfig {
}
