package com.example.bookcopy.web;

import com.example.bookcopy.domain.posts.Posts;
import com.example.bookcopy.domain.posts.PostsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import static org.hamcrest.CoreMatchers.containsString;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IndexControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    WebApplicationContext context;
    MockMvc mvc;

    @BeforeEach
    public void setup(){
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @AfterEach // 검증이 끝나면 지운다, 와 After가 아니라 AfterEach였다 대반전
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    public void MainPageLoad(){
        String body = this.restTemplate.getForObject("/", String.class);

        // 한글이 안나오길래 당황했는데, 스프링부트 버젼을 2.6.X로 변환하니 되었다
        assertThat(body).contains("스프링부트로 시작하는 웹 서비스");
    }

    // 이 아래부터는 내가 임의로 Test를 해보는 것이다 잘될랑가는 모르겠다, 안된다 ㅋㅋㅋ
    // 아마 이부분들도 인증이후에 필요한 것들이라 그런거 같다 까짓거해보지


    @Test
    @WithMockUser(roles = "USER")
    public void SavePageLoad() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/posts/save")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString("게시글 등록"))); // 확인해보기
        // 됐다 미쳤다 ㅋㅋㅋㅋ

        //String body = this.restTemplate.getForObject("/posts/save", String.class);

        //assertThat(body).contains("게시글 등록");
    }

    //@Test
    //@WithMockUser(roles = "USER")
    public void UpdatePageLoad() throws Exception{

        // 글을 업데이트하는거니 글 하나를 등록해야겠지?
        Posts savePost = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        mvc.perform(MockMvcRequestBuilders.get("/posts/update/" + savePost.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString("게시글 수정"))); // 확인해보기

        /*
            뭔가 이상하다 Test 순서에 따라 결과가 달라진다
            내가 임의로 만든 Delete Test의 요청 에러때문에 글이 등록만 되고 지워지지 않는거 같다
        * */

        //String body = this.restTemplate.getForObject("/posts/update/" + id, String.class);

        // 한글이 안나오길래 당황했는데, 스프링부트 버젼을 2.6.X로 변환하니 되었다
        //assertThat(body).contains("게시글 수정");
    }

}
