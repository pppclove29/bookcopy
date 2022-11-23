package com.example.bookcopy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookcopyApplication { // 프로젝트 최상단 위치, 모든 객체 읽기생성 자동설정하는 곳

	public static void main(String[] args) {
		// Tomcat 설치 불필요, 내장 WAS 사용, 스프링 부트로 만들어진 Jar 사용
		SpringApplication.run(BookcopyApplication.class, args);
	}

}
