package com.example.bookcopy.web;

import com.example.bookcopy.domain.posts.Posts;
import com.example.bookcopy.domain.posts.PostsRepository;
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
    @Autowired
    private PostsRepository postsRepository;

    @Test
    public void MainPageLoad(){
        String body = this.restTemplate.getForObject("/", String.class);

        // 한글이 안나오길래 당황했는데, 스프링부트 버젼을 2.6.X로 변환하니 되었다
        assertThat(body).contains("스프링 부트로 시작하는 웹서비스");
    }

    // 이 아래부터는 내가 임의로 Test를 해보는 것이다 잘될랑가는 모르겠다
    @Test
    public void SavePageLoad(){
        String body = this.restTemplate.getForObject("/posts/save", String.class);

        assertThat(body).contains("게시글 등록");
    }

    @Test
    public void UpdatePageLoad(){
        Long id = 1L;

        // 글을 업데이트하는거니 글 하나를 등록해야겠지?
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        String body = this.restTemplate.getForObject("/posts/update/" + id, String.class);

        // 한글이 안나오길래 당황했는데, 스프링부트 버젼을 2.6.X로 변환하니 되었다
        assertThat(body).contains("게시글 수정");
    }

}
