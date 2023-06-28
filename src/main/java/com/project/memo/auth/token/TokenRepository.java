package com.project.memo.auth.token;

import com.project.memo.domain.memoContent.memoContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    /*안써서 주석처리 나중에 지울수 있음*/
    @Query(value = "SELECT refreshToken from Token where email=:email", nativeQuery = true)
    String findRefreshToken(@Param("email") String email);

    @Query(value = "SELECT email from Token where refreshToken=:refresh", nativeQuery = true)
    String getFindEmail(@Param("refresh") String refresh);

//    @Transactional
//    @Modifying
//    @Query(value ="DELETE from Token where email=:email", nativeQuery = true)
//    int tokendelete(@Param("email") String email);

    @Transactional
    @Modifying
    @Query(value ="UPDATE Token set refreshToken=:refresh, accessToken=:access where email=:email", nativeQuery = true)
    int updateJWT(@Param("refresh") String refresh, @Param("access") String access,@Param("email") String email);

}
