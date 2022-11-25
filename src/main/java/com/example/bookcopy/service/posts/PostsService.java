package com.example.bookcopy.service.posts;

import com.example.bookcopy.domain.posts.Posts;
import com.example.bookcopy.domain.posts.PostsRepository;
import com.example.bookcopy.web.dto.PostsListResponseDto;
import com.example.bookcopy.web.dto.PostsResponseDto;
import com.example.bookcopy.web.dto.PostsSaveRequestDto;
import com.example.bookcopy.web.dto.PostsUpdateRequestDto;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // readonly를 사용하려면 이걸 써야한다
//import javax.transaction.Transactional; 이건 readonly가 없다

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service // 얜 뭐지? 핵심 서비스 담당 객체를 Bean으로 등록하는 것이다
public class PostsService {

    // 생각해보니 얘는 왜 자동 의존 주입 없나?
    // 역시 RequiredArgsConstructor 때문에
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

    @Transactional(readOnly = true) // 조회만 가능하면 성능이 상승한다
    public List<PostsListResponseDto> findAllDesc(){
        return postsRepository.findAllDesc().stream().map(PostsListResponseDto::new).collect(Collectors.toList());
        /*
            굉장히 길다 하나하나 살펴보자
            DB에 연결된 레포지토리의 선언한 findAllDesc를 통해 얻어온 List<Posts>를 스트림으로 변환한다
            map을 통하여 Stream<Posts>를 Stream<PostsListResponseDto>로 변환한다
            이렇게 나온 Stream을 toList를 통해 collect하여 List<>로 반환한다
        * */
    }
}
/*
    book p.106, 113
*/
