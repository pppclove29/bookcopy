package com.example.bookcopy.web;

import com.example.bookcopy.service.posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model){
        /*
            1. 사용자가 메인 화면으로 Get을 통해 들어온다
            2. 게시글 뭉탱이를 findAllDesc로 받아온다
            3. 서버는 model을 통해 템플릿 엔진에 posts라는 이름으로 객체를 전달한다
            4. 템플렛에서는 뭐 {{posts}} 등으로 접근 가능한가보다

            이게 맞나?
        * */
        model.addAttribute("posts",postsService.findAllDesc());
        return "index";
    } // -> src/main/resources/templates + /index + .mustache

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }
}
