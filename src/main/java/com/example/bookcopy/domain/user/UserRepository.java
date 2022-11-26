package com.example.bookcopy.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // User라는 옵셔널 객체를 반환한다
    Optional<User> findByEmail(String email);
}
