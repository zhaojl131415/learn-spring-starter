package com.zhao.elasticsearch.annotations;

import co.elastic.clients.elasticsearch._types.mapping.FieldType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TODO
 *
 * @author zhaojinliang
 * @version 1.0.0
 * @since 2022/12/26
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Field {
    String name() default "";

    FieldType type();

    String analyzer() default "";
}
