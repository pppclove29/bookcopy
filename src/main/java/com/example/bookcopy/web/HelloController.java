package com.example.bookcopy.web;

import com.example.bookcopy.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController // 얘가 controller랑 뭐가 다른지 왜 궁금해 하지 않았지? PostsApiController에서 찾아서 정리해둔다.
public class HelloController {
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/hello/dto") // book p.75
    public HelloResponseDto helloDto(@RequestParam("name") String name,
                                     @RequestParam("amount") int amount) {
        return new HelloResponseDto(name, amount);
    }
}
// book p.60 - 61