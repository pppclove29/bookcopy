package com.example.bookcopy.config.auth.dto.MakeLogTest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface MakeLog {
}
/*
   연습용 사용자 지정 애너테이션
* */