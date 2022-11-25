package com.example.bookcopy.web;

import com.example.bookcopy.service.posts.PostsService;
import com.example.bookcopy.web.dto.PostsResponseDto;
import com.example.bookcopy.web.dto.PostsSaveRequestDto;
import com.example.bookcopy.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

@RequiredArgsConstructor
@RestController // Controller + ResponseBody <- 얜 또 뭐야
/*
    @RequestMapping("/api/v1/posts/{id}")
    만약 모든 메소드의 주소값이 동일하면 이렇게 쓰고 PathValiable을 사용 할 수 있을까?
* */
@RequestMapping("/api/v1/posts")
public class PostsApiController {
    // 얘 왜 AutoWired없어?
    // RequiredArgsConstructor 이 친구가 해결해준다. 이친구는 생성자를 생성해주므로 자동으로 주입이 가능하다
    // 진짜 볼수록 놀랍다 어떻게 이렇게 최적화에 미친사람들이 있지?
    private final PostsService postsService;

    @PostMapping
    public Long save(
            @RequestBody PostsSaveRequestDto postsSaveRequestDto) {
        return postsService.save(postsSaveRequestDto);
    }

    /*
         PutMapping은 또 뭐란 말인가?

         RequestMapping의 method를 RequestMethod.PUT으로 한 것과 같은 역할을 한다

         <HTTP 개념>
         PUT 메소드는 멱등성을 가지는 요청으로 여러번을 보내던 한번 보내던 동일한 효과를 보인다
         수정 의 개념으로 알고 있자
    * */
    @PutMapping("/{id}") // {id}를 작성하자 id에 대한 변수가 없다며 화를 낸다
    public Long update(
            @PathVariable Long id,
            @RequestBody PostsUpdateRequestDto postsUpdateRequestDto) { // 위의 save 메소드와 동일하게 서버의 값을 변경하므로 dto를 통하여 상호작용한다
        return postsService.update(id, postsUpdateRequestDto);
    }

    @GetMapping("/{id}")
    public PostsResponseDto findById(
            @PathVariable Long id) {
        return postsService.findById(id);
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



    모든 메서드가 postService를 통하여 저장소와 상호작용하고있다.

    컨트롤러에서 사용자의 기본 정보와 요청값을 받아서 Dto를 통해 Service에 전달한다
    Service는 해당 요청에 맞는 서비스를 제공하며 에러 처리 및 값 수정등을 담당한다. 이때 Service는 Repositiory에 접근할 수 있다.
    작업 후 다시 컨트롤러가 작업을 받고 사용자에게 정보를 보여준다
* */
