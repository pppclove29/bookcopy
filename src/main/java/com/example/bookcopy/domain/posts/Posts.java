package com.example.bookcopy.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor // Dto에 사용된 RequiredArgsConstructor와 비슷한 맥락, 파라미터가 없는 생성자릉 생성한다
@Entity // 해당 선언으로 이 클래스는 DB에 매칭될 것임을 알린다, + 절대로 Setter를 사용하지 않는다
public class Posts {
    @Id // PK필드
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK 생성규칙
    private Long id;

    @Column(length =  500, nullable = false) // 칼럼의 기본값을 변경하고 싶을때 선언해서 사용
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;
/*
    기존 생성 패턴의 단점(코드 반복, 순서에 따른 에러 증가)을 보완하기 위해 고안된 방법, 찾다보니 끝도 없다 SOLID 원칙까지 갔었다.
    생성 패턴, 구조 패턴, 행동 패턴 등 굉장히 많은 패턴이 존재한다

    각 파라미터값을 유연하게 넣을 수 있고 보다 가벼운 생성자를 생성함으로써 확장성을 꾀한다.\

    중요한 개념이니 잘 숙지하고 있자.
*/
    @Builder
    public Posts(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
}
/*
    book p.89
    많은 JPA 애너테이션이 사용된다
    이러한 내용은 JPA 책을 통해 공부해 나아가자
    일단은 어느 애너테이션이 어느 역할을 한다 정도만 알도록 한다
*/
