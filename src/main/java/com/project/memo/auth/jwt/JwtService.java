package com.project.memo.auth.jwt;

import com.project.memo.auth.token.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class JwtService {

    /*
    JWT 생성
    @param userNum
    @return String
     */
    @Value("${jwt.token.secret.key}")
    private String JWT_SECRET_KEY;
    public Token createJwt(String userNum, String username){
        Date now = new Date();

        String accessToken = Jwts.builder()
                .setHeaderParam("type","jwt")
                .claim("userNum",userNum)
                .claim("username",username)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis()+1*(60 * 30))) //발급날짜 계산
                .signWith(SignatureAlgorithm.HS256,JWT_SECRET_KEY) //signature 부분
                .compact();

        String refreshToken = Jwts.builder()
                .setHeaderParam("type","jwt")
                .claim("userNum",userNum)
                .claim("username",username)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis()+1*(60 * 60 * 24 * 30))) //발급날짜 계산
                .signWith(SignatureAlgorithm.HS256,JWT_SECRET_KEY) //signature 부분
                .compact();
        return  Token.builder().accessToken(accessToken).refreshToken(refreshToken).email(userNum).build();
    }

    /*
    Header에서 X-ACCESS-TOKEN 으로 JWT 추출
    @return String
     */
//    public String getJwt(){
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//        return request.getHeader("X-ACCESS-TOKEN");
//    }

    /*
    JWT에서 userIdx 추출
    @return int
    @throws BaseException
     */
    public String getUserNum(String jwtToken){
        //1.JWT parsing
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET_KEY)
                .parseClaimsJws(jwtToken.replace("Bearer ", ""))
                .getBody();

        // 2.userNum 추출
        return claims.get("userNum", String.class);
    }

}