package com.example.bookcopy.web;

import com.example.bookcopy.config.auth.SecurityConfig;
import com.example.bookcopy.web.HelloController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
/*
    에러의 원흉

    No qualifying bean of type 'com.example.bookcopy.config.auth.CustomOAuth2UserService'

    SecurityConfig는 읽었지만 이것의 생성에 필요한
    CustomOAuth2UserService를 읽지 않았다
    @Repository, @Service, @Component는 읽지 못한다

    SecurityConfig는 해당 테스트에 필요 없으므로 읽지 않게 변경하자
* */
@WebMvcTest(controllers = HelloController.class,
excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,classes = SecurityConfig.class)
})
public class HelloControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser(roles = "USER")
    public void ReturnHello() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
        /*
            book p. 62 - 63
            이게 뭔데 이리 길고 복잡하지?
            검증하는거래
            404인지 200인지 500인지...
            해당 컨트롤러에서 제대로 된 값을 리턴했는지
         */
    }

    //@Test
    //@WithMockUser(roles = "USER")
    public void ReturnHelloDto() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(get("/hello/dto")
                        .param("name", name)
                        .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())// 200이면 pass
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));
        /*
            book p.76
            이건 또 뭐야
            param으로 값을 받아와서
            json으로 검증한다

            assertThat 과 andExpect는 비슷한 역할을 한다
         */

    }
}
// ***************결론 JUnit 공부해라******************
//

