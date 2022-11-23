package com.example.bookcopy.domain.posts;

import org.junit.After;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

    // 인터페이스를 상속한 인터페이스
    @Autowired
    PostsRepository postsRepository;

    @After //  Junit 단위 테스트가 끝날때마다 호출된데, 다른 클래스에서 끝난 테스트에서도 해당 메소드가 호출될까? 안되겠지?
    public void cleanup(){
        postsRepository.deleteAll();
    }

    @Test
    public void LoadPost(){
        String title = "테스트 게시글";
        String content = "테스트 본문";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("example@exam.com")
                .build()); // 저장소에 저장

        List<Posts> postsList = postsRepository.findAll(); // 이건 리스트로 나오나보다 모든게

        Posts posts = postsList.get(0); // 어차피 값 하나 밖에 없을테니까 그거 가져와서
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
        // 값 검증해라, 저장소에 넣었다 뺀거가 내가 설정한 값이여야하지, 아니면 뭔가 문제가 있는거지
    }
}
/*
    book p.96 - 97

    After이 뭔데? 위에 적어놨다
    Test 자꾸 에러난다
    import org.junit.jupiter.api.Test;로해야하는데 default로 설정 못할까?
* */
