package com.example.bookcopy.web;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IndexControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void MainPageLoad(){
        String body = this.restTemplate.getForObject("/", String.class);

        // 한글이 안나오길래 당황했는데, 스프링부트 버젼을 2.6.X로 변환하니 되었다
        assertThat(body).contains("스프링 부트로 시작하는 웹서비스");
    }
}
