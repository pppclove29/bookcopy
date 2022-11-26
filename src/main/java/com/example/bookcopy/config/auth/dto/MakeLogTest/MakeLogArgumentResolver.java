package com.example.bookcopy.config.auth.dto.MakeLogTest;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

@Component
public class MakeLogArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean a = parameter.getParameterAnnotation(MakeLog.class) != null;
        boolean b = parameter.getParameterType().equals(EmptyClass.class);

        log.println(a&&b);
        // 일단 통과는 했다 true가 출력됨은 확인할 수 있는데 아래 true를 반환하는거에서 아마 값 속성이 안맞아서 터진거같다

        return a && b;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return new EmptyClass(true);
    }
}

/*
    개인의 호기심 및 해당 함수 사용 숙련도 증가를 위해 사용자 지정 Resolver를 생성해봤지만
    받아오는게 영 시원치않다

    잡아냈다! Long 클래스는 실패했지만 테스트용 클래스는 잡는데 성공했다
* */
