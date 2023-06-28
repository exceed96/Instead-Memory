package com.project.memo.auth.jwt;

import com.project.memo.auth.token.Token;
import io.jsonwebtoken.*;
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
    public static final String ACCESS_TOKEN = "AccessToken";
    public static final String REFRESH_TOKEN = "RefreshToken";
    public Token createJwt(String userNum, String username){
        Date now = new Date();

        String accessToken = Jwts.builder()
                .setHeaderParam("type","jwt")
                .claim("userNum",userNum)
                .claim("username",username)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime()+ 1 * 1000*60)) //발급날짜 계산 유효기간 30분
                .signWith(SignatureAlgorithm.HS256,JWT_SECRET_KEY) //signature 부분
                .compact(); // 생성

        String refreshToken = Jwts.builder()
                .setHeaderParam("type","jwt")
                .setIssuedAt(now)//1*1000*60*60*24
                .setExpiration(new Date(now.getTime()+1*1000*60)) //발급날짜 계산 유효기간 1년 7일로 하고싶다면 1*(1000*60*60*24*7) 1000*60*60*24 1일
                .signWith(SignatureAlgorithm.HS256,JWT_SECRET_KEY) //signature 부분
                .compact();
        return  Token.builder().accessToken(accessToken).refreshToken(refreshToken).email(userNum).build();
    }
    public String createAccessToken(String userNum, String username){
        Date now = new Date();

        String accessToken = Jwts.builder()
                .setHeaderParam("type","jwt")
                .claim("userNum",userNum)
                .claim("username",username)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime()+1*(1000*60))) //발급날짜 계산 유효기간 30분 1000*60*30
                .signWith(SignatureAlgorithm.HS256,JWT_SECRET_KEY) //signature 부분
                .compact(); // 생성

        return  accessToken;
    }
    /* 토큰의 유효성 + 만료 일자 */
    public boolean validateToken(String jwtToken)
    {
        try{
            Claims claims = Jwts.parser().setSigningKey(JWT_SECRET_KEY).parseClaimsJws(jwtToken).getBody();
            return !claims.getExpiration().before(new Date());
        }catch(ExpiredJwtException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    // header 토큰을 가져오는 기능
    public static String getHeaderToken(HttpServletRequest request, String type) {
        return type.equals("Access") ? request.getHeader(ACCESS_TOKEN) :request.getHeader(REFRESH_TOKEN);
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