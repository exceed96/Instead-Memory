package com.project.memo.auth;

import com.project.memo.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@RequiredArgsConstructor
@EnableWebSecurity //springSecurity 설정 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 추가
//        http.cors().configurationSource(request -> {
//            var cors = new CorsConfiguration();
//            cors.setAllowedOrigins(List.of("http://localhost:3000"));
//            cors.setAllowedMethods(List.of("GET","POST", "PUT", "DELETE", "OPTIONS"));
//            cors.setAllowedHeaders(List.of("*"));
//            return cors;
//        });
        http
                .csrf().disable()
                .headers().frameOptions().disable() //h2-console을 사용하기 위해 쓴것.
                .and()
                .authorizeRequests() //URL별 관리설정하는 옵션
                .antMatchers("/","/css/**","/images/**","/js/**").permitAll()
                .antMatchers("/v1/**").hasRole(Role.USER.name()) //4
                .anyRequest().authenticated()//anyRequest -> 설정된 값 이외 나머지 URL 인증된 사용자만
                .and()
                .logout()
                .logoutSuccessUrl("/")// 로그아웃 성공시 루트주소로
                .and()
                .oauth2Login()//OAuth2로그인기능에 대한 여러 설정의 진입점
                .userInfoEndpoint()//OAuth2로그인 성공이후 사용자 정보를 가져올때의 설정
                .userService(customOAuth2UserService);//소셜로그인에대해 성공한다면 UserService인터페이스의 구현체를 등록
    }
}
