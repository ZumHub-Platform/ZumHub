package com.server.mapping.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface SecurityPolicy {

    String[] allowedOrigins() default {};

    boolean allowCredentials() default false;

    String[] allowedHeaders() default "";

    String[] allowedMethods() default "";

    String[] exposedHeaders() default "";

    int maxAge() default -1;
}
