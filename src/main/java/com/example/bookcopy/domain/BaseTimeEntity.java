package com.example.bookcopy.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
// 해당 설정은 createdDate, modifiedDate 또한 상속받는 Entity에서 하나의 컬럼으로 인식하도록 해준다
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
/*
    book p.121

    객체의 등록, 변경 등에서 날짜는 거의 필수 항목이다
    다만 이러한 날짜 변경 및 등록코드를 일일이 전부 적어주는것은 코드가 더러워지고 중복도 많아진다

    Entity를 사용하는 객체에 상속시켜서 생성, 변경 시 자동으로 값이 들어가도록 바꿀수있다.

* */
