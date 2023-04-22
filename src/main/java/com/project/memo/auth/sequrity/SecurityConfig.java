package com.project.memo.auth.sequrity;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity //springSecurity 설정 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable() //h2-console을 사용하기 위해 쓴것.
                .and()
                .authorizeRequests() //URL별 관리설정하는 옵션
                .antMatchers("/","/css/**","/images/**","/js/**","/**").permitAll()
                .antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                .anyRequest().authenticated()//anyRequest -> 설정된 값 이외 나머지 URL 인증된 사용자만
                .and()
                .logout().permitAll()
                .and()
                .formLogin();
    }
}
