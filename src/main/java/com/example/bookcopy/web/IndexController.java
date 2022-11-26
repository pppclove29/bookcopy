package com.example.bookcopy.web;

import com.example.bookcopy.config.auth.dto.SessionUser;
import com.example.bookcopy.service.posts.PostsService;
import com.example.bookcopy.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;


@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model){
        /*
            1. 사용자가 메인 화면으로 Get을 통해 들어온다
            2. 게시글 뭉탱이를 findAllDesc로 받아온다
            3. 서버는 model을 통해 템플릿 엔진에 posts라는 이름으로 객체를 전달한다
            4. 템플렛에서는 뭐 {{posts}} 등으로 접근 가능한가보다

            최종적으로 모든 글을 보여준다

            이게 맞나?
        * */
        model.addAttribute("posts",postsService.findAllDesc());

        //CustomOAuth2UserService에서 user를 Key로 SessionUser을 저장했다
        //다시 꺼낼땐 다운캐스팅이 필요하다
        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        if(user!=null){
            model.addAttribute("userName", user.getName());
            //html에 존재하는 {{username}}에 사용할 userName을 모델에 싣는다
        }

        return "index";
    } // -> src/main/resources/templates + /index + .mustache

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){

        //글 하나를 찾는다
        PostsResponseDto dto = postsService.findById(id);
        //해당 글을 posts로 쏜다
        model.addAttribute("post", dto);

        return "posts-update";
    }
}
