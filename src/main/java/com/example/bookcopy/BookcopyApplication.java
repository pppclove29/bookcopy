package com.example.bookcopy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// HelloControllerTest에서 이것까지 스캔해서 에러터졌다. Entity가 없는데 jpa를 사용해서, config 패키지로 옮긴다
//@EnableJpaAuditing JPA에서 auditing(감사, 감시)을 허락한다
@SpringBootApplication
public class BookcopyApplication { // 프로젝트 최상단 위치, 모든 객체 읽기생성 자동설정하는 곳

	public static void main(String[] args) {
		// Tomcat 설치 불필요, 내장 WAS 사용, 스프링 부트로 만들어진 Jar 사용
		SpringApplication.run(BookcopyApplication.class, args);
	}
}
