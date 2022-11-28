package com.example.bookcopy.config.auth;

import com.example.bookcopy.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
    private final HttpSession httpSession;

    //특정 파라미터에 대해 작업을 수행할 것인지를 정의한다.
    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        // 파라미터에 @Login이 달려있는지 확인한다
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;

        // SessionUser클래스와 파라미터가 동일한지 확인한다
        // getParameterType() 를 getParameter() 로 잘못 자동완성 시켜서 에러가 터졌었다
        boolean isUserClass = parameter.getParameterType().equals(SessionUser.class);

        /*
            해당 부분에 log를 해본 결과 단 한번만 실행된다.
            최적화를 위해 여러번 실행되지 않는듯하다
        * */

        return isLoginUserAnnotation && isUserClass;
    }

    // 실질적인 파라미터 값 주입에 사용되지만 여기선 그냥 세션을 리턴했다
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return httpSession.getAttribute("user");
    }
}
