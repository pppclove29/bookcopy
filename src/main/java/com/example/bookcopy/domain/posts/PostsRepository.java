package com.example.bookcopy.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Posts, Long> {
    /*
        메소드가 굉장히 많다
        한번 찾아보자

        공식문서
        https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html
    * */
}
/*
    book p.95
    DB Layer 접근자  <-- 이게 뭔데?

    DB와 접근할때마다 새로운 객체를 만들고 없애는건 비효율적이니
    미리 담당 객체 하나 만들어놓고 돌려쓰자 라는 취지인것 같음

    근데 이렇게 아무것도 없이 인터페이스 하나 끌고와서 했는데 이게 자동으로 명령어가 되네?
    진짜 알다가도 모르겠다
 */
