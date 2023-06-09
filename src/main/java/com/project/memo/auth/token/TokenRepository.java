package com.project.memo.auth.token;

import com.project.memo.domain.memoContent.memoContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    /*안써서 주석처리 나중에 지울수 있음*/
//    @Query(value = "SELECT refreshToken from Token where email=:email", nativeQuery = true)
//    String findRefreshToken(@Param("email") String email);

}
