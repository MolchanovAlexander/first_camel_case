package com.example.first_camel_case.config;

import com.example.first_camel_case.model.DniweSource;
import com.example.first_camel_case.model.SomeSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class BeanConfig {

    @Lazy
    @Bean("PostgreSQL")
    public SomeSource hikari() {
        return new DniweSource();
    }
}
