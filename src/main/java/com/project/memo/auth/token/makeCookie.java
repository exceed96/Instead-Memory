package com.project.memo.auth.token;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
@Component
@NoArgsConstructor
public class makeCookie {
    public Cookie createCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        // 쿠키 속성 설정
        cookie.setHttpOnly(true);  //httponly 옵션 설정
//        cookie.setSecure(true); //https 옵션 설정
        cookie.setPath("/"); // 모든 곳에서 쿠키열람이 가능하도록 설정
        cookie.setMaxAge(60 * 60 * 24); //쿠키 만료시간 설정
        return cookie;
    }
}
