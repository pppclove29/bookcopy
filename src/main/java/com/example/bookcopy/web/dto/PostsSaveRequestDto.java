package com.example.bookcopy.web.dto;

import com.example.bookcopy.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private String author;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }


    public Posts toEntity(){
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
}
/*
    book p.107

    이 친구는 뭘까? 라고 생각하기전에 책 101page를 참고하자
    이 친구는 Controller와 Service 사이의 데이터 전달을 위해 존재한다.

    일단 Posts(Entity)랑 굉장히 비슷하다

    Entity는 매우 중요한 클래스이고 변경이 최소화 되어야한다
    그러기 위해 비슷한 Dto를 사용하여 대신 사용한다(View layer와 DB layer의 분리)
* */
