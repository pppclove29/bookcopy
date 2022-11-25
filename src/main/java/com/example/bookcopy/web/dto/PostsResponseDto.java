package com.example.bookcopy.web.dto;

import com.example.bookcopy.domain.posts.Posts;
import lombok.Getter;

@Getter
public class PostsResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author;

    //전체 값중 일부만 쓴다는데 뭐 다 쓰는데 그냥? 일부만 쓸거면 그냥 빌더 쓰는게 낫지않나?
    public PostsResponseDto(Posts entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
    }
}
