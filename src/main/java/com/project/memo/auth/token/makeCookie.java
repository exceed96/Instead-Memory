package com.project.memo.auth.token;

import lombok.NoArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
@Component
@NoArgsConstructor
public class makeCookie {
    public ResponseCookie createCookie(String name, String value) {
//        Cookie cookie = new Cookie(name, value);
        ResponseCookie cookie = ResponseCookie.from(name, value)
                .httpOnly(true) //httponly 옵션 설정
                .secure(true) //https 옵션 설정
                .path("/") // 모든 곳에서 쿠키열람이 가능하도록 설정
                .domain("insteadmemo.kr")
                .maxAge(60 * 60 * 24) //쿠키 만료시간 설정
                .sameSite("lax")
                .build();
        // 쿠키 속성 설정
//        cookie.setHttpOnly(false);  //httponly 옵션 설정
//        cookie.setSecure(false); //https 옵션 설정
//        cookie.setPath("/"); // 모든 곳에서 쿠키열람이 가능하도록 설정
//        cookie.setMaxAge(60 * 60 * 24); //쿠키 만료시간 설정
        return cookie;
    }
}
