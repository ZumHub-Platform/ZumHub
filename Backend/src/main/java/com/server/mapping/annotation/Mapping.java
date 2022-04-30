package com.server.mapping.annotation;

import com.server.request.RequestMethod;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.METHOD})
public @interface Mapping {

    String path() default "/";

    RequestMethod[] method() default RequestMethod.GET;

    String[] parameters() default {};

    String[] headers() default {};
}
