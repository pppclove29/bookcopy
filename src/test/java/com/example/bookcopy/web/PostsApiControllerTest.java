package com.example.bookcopy.web;

import com.example.bookcopy.domain.posts.Posts;
import com.example.bookcopy.domain.posts.PostsRepository;
import com.example.bookcopy.web.dto.PostsSaveRequestDto;
import com.example.bookcopy.web.dto.PostsUpdateRequestDto;
import org.junit.After;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// 스프링 러너를 스프링 실행자로 쓴다는데 잘 모르겠음
@RunWith(SpringRunner.class)
// 스프링부트테스트가 없으면 터짐
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    // 랜덤 포트를 설정해 줘야 로컬서버포트가 제대로 실행되는거 같음
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @After // 검증이 끝나면 지운다
    public void tearDown() throws Exception{
        postsRepository.deleteAll();
    }

    @Test
    public void PostsRegi() throws Exception{
        String title = "title";
        /*
            오타를 냈다고 가정하고 titel이라고 바꿔보자

            전혀 상관이 없다. 이 과정 자체는 특정 Dto를 바디로 만들어서 사이트로 요청을 보내는 것이다
            요청이 잘 갔는지 ?  (네트워크 체크 및 url 체크)
                assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
            요청의 바디는 잘 들어있는지?   (네트워크 체크 및 요청 값 일치)
                assertThat(responseEntity.getBody()).isGreaterThan(0L);
            요청으로 저장된 저장소의 값이 초기값과 일치하는지 ? (저장 및 불러오기)
                List<Posts> all = postsRepository.findAll();
                assertThat(all.get(0).getTitle()).isEqualTo(title);
                assertThat(all.get(0).getContent()).isEqualTo(content);

            이러한 과정을 검수하지 특정 값에 대한 값 검수가 아니기 때문에 테스트는 무사하게 통과된다.
        * */
        String content = "content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("author")
                .build(); // 검증 데이터를 새로 만들고

        // 서버가 열린 후 해당 url로 요청을 보내보자
        String url = "http://localhost:" + port + "/api/v1/posts";



        /*
            얜 또또 뭘까 간단하게만 검색해서 찾아보자
            1. ResponseEntity : HttpRequest를 상속 -> 결과 데이터와 HTTP 상태코드를 직접 제어 가능
                                헤더, 바디 등 내용이 나오는데 이는 종명이한테 네트워크 강의 받고 나서 다시 찾아보자
            2. restTemplate   : Rest Api 요청 후 응답까지 되도록 설계되었다. 여러가지 메소드를 가지고 있다
            2-1. postForEntity: HTTP Post 요청 후 결과는 ResponseEntity로 반환
        * */
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    public void PostsRevise() throws Exception{
        Posts savedPosts = postsRepository.save(Posts.builder()
                                                        .title("title")
                                                        .content("content")
                                                        .author("author")
                                                        .build()); // 업데이트할 게시글을 만든다

        Long updateID = savedPosts.getId();
        String E_title = "E_title";
        String E_content = "E_content"; // 기존 게시글의 ID를 가져오고 변경될 변수들을 설정한다

        // 서버에 요청할 바디를 생성한다
        PostsUpdateRequestDto postsUpdateRequestDto = PostsUpdateRequestDto.builder()
                                                                            .title(E_title)
                                                                            .content(E_content)
                                                                            .build();

        String url =  "http://localhost:" + port + "/api/v1/posts/" + updateID;

        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(postsUpdateRequestDto);

        /*
            Put친구는 뒤지게 까다롭다 putforEntity도 없다
            exchange를 사용하고 HttpMethod. PUT을 써넣어줘야하며 바디도 HttpEntity를 사용해야한다
            그렇다면 위에 PostsRegi도 동일하게 할 수 있을까?

            테스트는 통과하는거보니 문제는 없는거 같다 다만 코드를 줄이기 위해 혹은 모종의 이유로 post어저구를 쓰는거 같다
        * */
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(E_title);
        assertThat(all.get(0).getContent()).isEqualTo(E_content);

        // 테스트 실행결과 insert와 update가 제대로 실행됨을 알 수 있다.
    }
}

/*
    테스트를 하고 싶으면 원하는 테스트 블럭을 실행하면 된다
    @RunWith로 테스트 도중 함께 돌릴 클래스를 지정
    @SprintBootTest는 일단 필요
    @Test나 @After에는 throw Exception을 기본으로 탑재하는 것이 좋아보인다
* */