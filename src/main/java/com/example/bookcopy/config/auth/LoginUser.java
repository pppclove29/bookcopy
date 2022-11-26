package com.example.bookcopy.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser {
}
/*
    첫 사용자 지정 어노테이션을 만들어봤다
    이후 이것을 사용하기 위해선 WebMvcConfigurer에 등록해야한다

    해당 어노테이션을 생성했을 당시 WebMvcConfigurer가 없으므로 새로 생성한다(WebConfig)
* */
