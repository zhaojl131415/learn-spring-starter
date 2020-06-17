package com.zhao.spring.boot.autoconfigure.config;

import com.zhao.spring.boot.autoconfigure.entity.Cat;
import com.zhao.spring.boot.autoconfigure.entity.Dog;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnimalConfig {

    @Bean
    public Dog dog() {
        return new Dog();
    }

    @Bean
    public Cat cat() {
        return new Cat();
    }
}
