package com.example.bookcopy.web.dto;

import com.example.bookcopy.domain.posts.PostsRepository;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsUpdateRequestDto { // 게시글 업데이트시 제목과 내용만을 변경할 수 있을거 같다

    private String title;
    private String content;

    @Builder
    public PostsUpdateRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
