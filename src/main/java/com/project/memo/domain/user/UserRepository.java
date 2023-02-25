package com.project.memo.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<users,Integer> {
    //Optional<T> null이 올수 있는 값을 감싸는 wrapper 로 참조해도 NullPointerException 발생 ㄴㄴ
    Optional<users> findByEmail(String email);
    //findByEmail - 소셜 로그인으로 반환되는 값중 email을 통해 생성된 사용자인지 처음가입하는지 판단.
}