package com.project.memo.auth.token;

import com.project.memo.domain.memoContent.memoContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    @Query(value = "SELECT refreshToken from Token where email=:email", nativeQuery = true)
    String findRefreshToken(@Param("email") String email);

}
