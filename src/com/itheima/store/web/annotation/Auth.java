package com.itheima.store.web.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {
	// Ù–‘
	public String value() default "user";
}
