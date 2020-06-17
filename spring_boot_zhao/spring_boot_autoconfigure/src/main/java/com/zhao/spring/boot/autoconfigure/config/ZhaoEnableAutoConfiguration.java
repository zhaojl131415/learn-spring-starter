package com.zhao.spring.boot.autoconfigure.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(RoleImportSelector.class)
public @interface ZhaoEnableAutoConfiguration {
}
