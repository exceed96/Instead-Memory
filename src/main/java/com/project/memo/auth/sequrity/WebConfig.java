package com.project.memo.auth.sequrity;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration //configuraion을 통해 설정파일이라는 것을 알려줌
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry)
    {
        registry.addMapping("/**") //CORS를 적용할 URL패턴을 정의할 수 있다. -> 와일드 카드를 사용할 수 있음
        //또한 Ant-style도 지원하며 "/somePath/**"이렇게 적용할 수도있다.
                .allowedOrigins("http://localhost:8080")
                .allowedMethods("GET", "POST")
                .maxAge(3000);
    }

}
