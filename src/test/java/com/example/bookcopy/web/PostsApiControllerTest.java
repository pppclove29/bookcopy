package com.example.bookcopy.web;

import com.example.bookcopy.domain.posts.Posts;
import com.example.bookcopy.domain.posts.PostsRepository;
import com.example.bookcopy.web.dto.PostsDeleteRequestDto;
import com.example.bookcopy.web.dto.PostsSaveRequestDto;
import com.example.bookcopy.web.dto.PostsUpdateRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
// 스프링부트테스트가 없으면 터짐
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    // 랜덤 포트를 설정해 줘야 로컬서버포트가 제대로 실행되는거 같음
    @LocalServerPort
    private int port;


    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build(); // 자주보던 빌더의 모습이다
    }


    // 이거 메소드 너무 많다 일단 exchange로 해보자
    @Autowired
    private TestRestTemplate restTemplate;


    @Autowired
    private PostsRepository postsRepository;

    @AfterEach // 검증이 끝나면 지운다
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    /*
    302 리다이렉팅 에러, 권한이 없는 사용자를 자동으로 리다이렉트해서 생긴 에러다
    테스트시 임시로 권한을 부여한다
    될줄 알았는데 전혀 되지 않았다

    이유 : 해당 애너테이션은 MokeMvc에서만 작동한다고 한다, 즉 해당 클래스에 MockMvc를 가져와서 사용해야한다

    @After에 이은 @Before을 사용한다
     */
    @WithMockUser(roles = "USER")
    public void PostsSave() throws Exception {
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

        /*
            ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(responseEntity.getBody()).isGreaterThan(0L);
            인증에 사용될 권한을 부여받기위해 MockMvc를 사용한 인증을 시도한다
        */

        // mockMvc가 null일 수 있다며 에러를 벹는다, 교재와 동일하게도 해봤지만 안되는걸보면 컴파일러가 깐깐해진듯하다

        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());


        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void PostsUpdate() throws Exception {
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("title_for_update")
                .content("content_for_update")
                .author("author_for_update")
                .build()); // 업데이트할 게시글을 만든다

        Long updateID = savedPosts.getId();
        String E_title = "E_title_for_update";
        String E_content = "E_content_for_update"; // 기존 게시글의 ID를 가져오고 변경될 변수들을 설정한다

        // 서버에 요청할 바디를 생성한다
        PostsUpdateRequestDto postsUpdateRequestDto = PostsUpdateRequestDto.builder()
                .title(E_title)
                .content(E_content)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + updateID;


        /*
            Put친구는 뒤지게 까다롭다 putforEntity도 없다
            exchange를 사용하고 HttpMethod. PUT을 써넣어줘야하며 바디도 HttpEntity를 사용해야한다
            그렇다면 위에 PostsRegi도 동일하게 할 수 있을까?

            테스트는 통과하는거보니 문제는 없는거 같다 다만 코드를 줄이기 위해 혹은 모종의 이유로 post어저구를 쓰는거 같다

            HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(postsUpdateRequestDto);
            ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(responseEntity.getBody()).isGreaterThan(0L);

            권한 부여를 위해 역시나 mockMvc로 교체
        * */

        mockMvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(postsUpdateRequestDto)))
                .andExpect(status().isOk());

        List<Posts> all = postsRepository.findAll();

        // 3개나 처들어가있다 뭔가 어디선가 지우질 않았다
        log.println(all.size());

        assertThat(all.get(0).getTitle()).isEqualTo(E_title);
        assertThat(all.get(0).getContent()).isEqualTo(E_content);


    }


    @Test // 책에 없는 내가 임의로 만든 Test, MockMvc역시 따라해보자
    @WithMockUser(roles = "USER")
    public void Delete() throws Exception {
        // 생각을 해보자
        // 글 생성 -> 그 글을 삭제 -> DB에 제대로 반영되었는지 확인

        Long deleteID = 1L;

        postsRepository.save(Posts.builder()
                .title("title_for_delete")
                .content("content_for_delete")
                .author("author_for_delete")
                .build()); // 글 하나 생성

        PostsDeleteRequestDto requestDto = PostsDeleteRequestDto
                .builder()
                .id(deleteID)
                .build();

        /*
            HttpEntity<PostsDeleteRequestDto> requstEntity= new HttpEntity<>(requestDto);


            ResponseEntity<Long> res = restTemplate.exchange(url,HttpMethod.DELETE, requstEntity,Long.class);

            assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(res.getBody()).isGreaterThan(0L);
         */

        String url = "http://localhost:" + port + "/api/v1/posts/" + deleteID;

        // 테스트 실패, delete는 405에러가 난다
        mockMvc.perform(delete(url));

        assertThat(postsRepository.findById(deleteID)).isEmpty();
    }
}
// 스프링 러너를 스프링 실행자로 쓴다는데 잘 모르겠음

/*
    테스트를 하고 싶으면 원하는 테스트 블럭을 실행하면 된다
    @RunWith로 테스트 도중 함께 돌릴 클래스를 지정
    @SprintBootTest는 일단 필요
    @Test나 @After에는 throw Exception을 기본으로 탑재하는 것이 좋아보인다
* */