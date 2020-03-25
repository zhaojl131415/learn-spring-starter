package com.zhao.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import java.util.List;

@Component
@PropertySource("classpath:test.yml")
@Getter
@Setter
@ConfigurationProperties
public class TestProperties {
//    @Value("${name}")
    private String name;

}
