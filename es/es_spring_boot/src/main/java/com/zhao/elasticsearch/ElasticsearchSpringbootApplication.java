package com.zhao.elasticsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class ElasticsearchSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElasticsearchSpringbootApplication.class, args);
	}

}
