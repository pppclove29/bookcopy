package com.example.bookcopy.domain.posts;

import org.junit.After;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

    // 인터페이스를 상속한 인터페이스
    @Autowired
    PostsRepository postsRepository;

    @After //  Junit 단위 테스트가 끝날때마다 호출된데, 다른 클래스에서 끝난 테스트에서도 해당 메소드가 호출될까? 안되겠지? 결론은 안된다
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

    @Test
    public void BaseTimeRegi(){ // PostsEntity 생성 및 등록에 Time이 잘 달리는지 확인해본다
        LocalDateTime test_time = LocalDateTime.of(2022,10,10,0,0,0);

        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build()); // DB저장은 기존과 동일하다 = 우리는 BaseTimeEntity를 통해 코드 수정을 최소화 할 수 있다!

        List<Posts> all = postsRepository.findAll();

        System.out.println(">>>>>>>>>>>>>> createDate = " + all.get(0).getCreatedDate());
        System.out.println(">>>>>>>>>>>>>> modifiedDate = " + all.get(0).getModifiedDate());

        // isAfter은 날짜를 비교하는 형식으로 test_time보다 미래일 경우 테스트를 통과한다
        assertThat(all.get(0).getCreatedDate()).isAfter(test_time);
        assertThat(all.get(0).getModifiedDate()).isAfter(test_time);
    }
}
/*
    book p.96 - 97

    After이 뭔데? 위에 적어놨다
    Test 자꾸 에러난다
    import org.junit.jupiter.api.Test;로해야하는데 default로 설정 못할까?

    book p.122

* */
