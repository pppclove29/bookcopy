package com.example.bookcopy.web;

import com.example.bookcopy.config.auth.LoginUser;
import com.example.bookcopy.config.auth.dto.MakeLogTest.EmptyClass;
import com.example.bookcopy.config.auth.dto.MakeLogTest.MakeLog;
import com.example.bookcopy.config.auth.dto.SessionUser;
import com.example.bookcopy.service.posts.PostsService;
import com.example.bookcopy.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;


@RequiredArgsConstructor
@Controller
public class IndexController {
    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        /*
            1. 사용자가 메인 화면으로 Get을 통해 들어온다
            2. 게시글 뭉탱이를 findAllDesc로 받아온다
            3. 서버는 model을 통해 템플릿 엔진에 posts라는 이름으로 객체를 전달한다
            4. 템플렛에서는 뭐 {{posts}} 등으로 접근 가능한가보다

            최종적으로 모든 글을 보여준다

            이게 맞나?
        * */
        model.addAttribute("posts", postsService.findAllDesc());

        /*
            CustomOAuth2UserService에서 user로 저장한 usersession을 꺼내온다
            이 부분은 향후 확장성 및 유지보수 측면에서 별로라고 한다.
            LoginUserArgumentResolver에 새롭게 사용할수있게 설정하자

            이 부분을 삭제했다 -> SessionUser user = (SessionUser) httpSession.getAttribute("user");

            이제는 파라미터 값으로 @LoginUser를 통해 user를 받아온다

            지금까지 배운 내용 중 가장 이해가 안된다

            지금까지 공부하면서 애너테이션 내부 로직까지 확인은 안해봤는데 @LoginUser 애너테이션 내부는 정말 아무것도 없었다
            그리고 추가로 손본곳이 WebConfig와 LoginUserArgumentResolver인데 두 클래스의 메소드를 딱히 사용하거나 하지않았다

            그렇다면 빌드시 자동으로 스캔해서 사용한다는 얘기인데

            WebConfig는 @Configuration, LoginUserArgumentResolver는 @Component 애너테이션이 존재한다

            이 둘의 차이를 명확하게 할 필요가 있어보인다
            해당 글을 참고했다 -> https://m.blog.naver.com/sthwin/222131873998

            @Configuration는 내부의 @Bean 메소드 호출 시 스프링 컨텍스트에 등록되어있던 빈을 반환한다
            @Component는 @AutoWired가 적용되어있지 않은 이상 스프링 컨텍스트에 등록된 빈을 반환하지 않는다

            책에서는 다음과 같이 사용한다
            @Configuration : 사용자 지정 빈을 직접 선언해서 만들어 관리, 하나의 클래스에서 관리하기 편리
            @Component : 여기저기 흩어져있는 클래스에 선언하여 빈으로써 스캔

            대부분 인터넷 글은 다음과 같이 설명한다
            개발자가 직접 제어 불가능 : @Configuration, @Bean
            개발자가 직접 제어 가능 : @Component

            즉, LoginUserArgumentResolver는 스캔 대상이 되어 자동으로 Bean으로 등록된다.
            WebConfig에서는 해당 Bean객체를 생성자를 통한 자동 의존 주입을 통해 사용한다

            글 하나를 더 찾았다
            @PathVariable과 @RequestBody 또한 값을 받아올때 HandlerMethodArgumentResolver를 사용한다고 한다

            그렇다면 이해가 된다. @LoginUser 또한 HandlerMethodArgumentResolver를 사용하고 해당 조건에 해당하므로
            세선이 반환된다.

            그런데 다른 코드에서도 이미 @PathVariable과 @RequestBody 를 많이 사용했다

            의문 1. HandlerMethodArgumentResolver가 없었을 때에는 왜 기존에는 잘 돌아갔는가?
            의문 2. 기본값이 정해져 있어서 잘 돌아갔던거라면 어떻게 @LoginUser만 WebConfig에 영향을 받게 할 수 있는가?

            글을 뒤져본 결과로 어림잡아 추측해보면 일단 모든 파라미터에 대해서 핸들러가 작동하고
            그 중 Resolver에 부합한 파라미터가 나오면 아래에 값 변조를 실행하는 것 같다
            모든 핸들러가 실행되기전 파라미터 값을 인터셉트하여 값을 대조 및 변조한다

            그렇다면 한번 동일하게 만들어서 값을 변조해보자
            @Pathvaliable을 Resolver로 감지해서 값을 변조해보자 목표는 postsUpdate 메소드이다

        */

        if (user != null) {
            model.addAttribute("userName", user.getName());
        }

        return "index";
    } // -> src/main/resources/templates + /index + .mustache

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model, @MakeLog EmptyClass emptyClass) {

        //글 하나를 찾는다
        PostsResponseDto dto = postsService.findById(id);
        //해당 글을 posts로 쏜다
        model.addAttribute("post", dto);

        if(emptyClass.isT())
            log.println("진실!");
        else
            log.println("거짓!");

        return "posts-update";
    }
}
