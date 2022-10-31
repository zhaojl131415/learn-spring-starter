package com.zhao.redis.config;

import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * TODO
 *
 * @author zhaojinliang
 * @version 1.0.0
 * @since 2022/9/9
 */
@Configuration
@ConfigurationProperties(
        prefix = "spring.redis"
)
public class RedissonConfiguration {
    private String host;
    private String port;
    private String password;

    @Bean(destroyMethod="shutdown")
    Redisson redisson() throws IOException {
        String address = "redis://".concat(host).concat(":").concat(port);
        Config config = new Config();
        config.useSingleServer().setAddress(address)
                .setConnectionPoolSize(30)
                .setConnectionMinimumIdleSize(10)
                .setPassword(password);
        return (Redisson) Redisson.create(config);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
