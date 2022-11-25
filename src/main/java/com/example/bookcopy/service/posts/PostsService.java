package com.example.bookcopy.service.posts;

import com.example.bookcopy.domain.posts.Posts;
import com.example.bookcopy.domain.posts.PostsRepository;
import com.example.bookcopy.web.dto.PostsResponseDto;
import com.example.bookcopy.web.dto.PostsSaveRequestDto;
import com.example.bookcopy.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service // 얜 뭐지? 핵심 서비스 담당 객체를 Bean으로 등록하는 것이다
public class PostsService {

    // 생각해보니 얘는 왜 자동 의존 주입 없나?
    private final PostsRepository postsRepository;

    @Transactional // 일관성? 고립성? 을 만족시켜주는 어노테이션이다
    public Long save(PostsSaveRequestDto postsSaveRequestDto){
        return postsRepository.save(postsSaveRequestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto postsUpdateRequestDto){
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " +id));

        //새로운 수정 요청에 대해서 요청을 분해하고 Entity를 업데이트한다
        posts.update(postsUpdateRequestDto.getTitle(), postsUpdateRequestDto.getContent());

        /*
            진짜다 책에서 본것과 같이 쿼리문을 DB로 날리는 문장이 존재하지 않는다
            그저 posts를 찾아서 posts 자체에서 수정하는 작업 밖에 없다

            -> jpa의 영속성 컨텍스트가 이것을 가능하게 한다

            0. 엔티티 매니저가 활성화 되어있을 것
            1. Transactional 내에서 데이터를 가져올 것
            2. 데이터를 변경할 것
            3. 트랜젝션이 끝나면 자동으로 변경된 데이터가 반영된다

            더티체킹이라고 한다
        * */

        return id;
    }

    public PostsResponseDto findById(Long id){
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(("해당 게시글이 없습니다. id = ") + id));

        return new PostsResponseDto(entity);
    }
}
/*
    book p.106, 113
*/
