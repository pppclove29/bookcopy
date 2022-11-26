package com.example.bookcopy.config;

import com.example.bookcopy.config.auth.LoginUserArgumentResolver;
import com.example.bookcopy.config.auth.dto.MakeLogTest.MakeLogArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final LoginUserArgumentResolver loginUserArgumentResolver;
    private final MakeLogArgumentResolver makeLogArgumentResolver;

    // argumentResolvers에 loginUserArgumentResolver를 추가한다
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers){
        argumentResolvers.add(loginUserArgumentResolver);
        argumentResolvers.add(makeLogArgumentResolver);
    }

}
