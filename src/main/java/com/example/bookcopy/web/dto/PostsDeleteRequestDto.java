package com.example.bookcopy.web.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostsDeleteRequestDto {

    private Long id;

    @Builder
    public PostsDeleteRequestDto(Long id){
        this.id = id;
    }
}
