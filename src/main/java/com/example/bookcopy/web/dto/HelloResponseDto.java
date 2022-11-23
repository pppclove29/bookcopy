package com.example.bookcopy.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class HelloResponseDto {
    // DTO = 계층 간 데이터 교환을 위한 자바 빈즈

    private final String name;
    private final int amount;
}
/*
    book p.72
    error: variable amount not initialized in the default constructor <- ????????????????

    원인 : Gradle 버젼이 높아서 build.gradle의 롬복 의존성 추가 방법에 문제가 있었던것이였음

    해결 :
    implementation 'org.projectlombok:lombok'
    -> 변경
    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'
*/