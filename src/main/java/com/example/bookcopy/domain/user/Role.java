package com.example.bookcopy.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    GUEST("ROLE_GUEST","손님"), USER("ROLE_USER","일반 사용자");

    private final String key;
    private final String title;
}
/*
   이건 신박하다
   아래 선언한 key와 title에 의해 위 Enum요소들이 결정된다

   순서 바꾸면 안됨 존나 모르겠음
* */
