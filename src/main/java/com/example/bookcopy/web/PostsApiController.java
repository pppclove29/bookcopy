package com.example.bookcopy.web;

import com.example.bookcopy.service.posts.PostsService;
import com.example.bookcopy.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController // Controller + ResponseBody <- 얜 또 뭐야
public class PostsApiController {
    // 얘 왜 AutoWired없어?
    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto postsSaveRequestDto){
        return postsService.save(postsSaveRequestDto);
    }
}
/*
    RequestBody는 HttpRequest를 객체에 매핑, Http본문을 Java 객체로 자동 역직렬화
    ResponseBody는 객체를 JSON으로 자동으로 직렬화

    일단 직렬화는?    객체를 통째로 변형하여 전송 가능하게 만든다
    역직렬화는?       반대겠지, 통짜 데이터를 분해하여 객체로 만든다

    결과적으로 저걸 쓰면 구현이 단순화된다.
    메소드에 @ResponseBody를 안써도되거든

    또 하나의 의문

    @RequestBody랑 @RequestParam 차이는 뭐야?

    Param은 객체 하나씩 이름 명명해서 받는거같고
    $http.post('http://localhost:7777/scan/l/register?username="Johny"&password="123123"&auth=true')
      .success(function (data, status, headers, config)

    Body는 scope로 뭉탱이를 기정해서 주는거고 그걸 통째로 역직렬화를 수행한다
* */
