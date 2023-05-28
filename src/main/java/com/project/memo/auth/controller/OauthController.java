package com.project.memo.auth.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.memo.auth.model.GetSocialOAuthRes;
import com.project.memo.auth.service.OauthService;
import com.project.memo.auth.token.makeCookie;
import com.project.memo.auth.token.tokenService;
import com.project.memo.auth.type.SocialLoginType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://43.200.92.244:8000")
@RequestMapping(value = "/auth")
@Slf4j
public class OauthController {
    private final OauthService oauthService;
    private final makeCookie makeCookie;
    private final tokenService tokenService;
    @RequestMapping(value = "/")
    public String main() {
       return "index";
    }
    /**
     * 사용자로부터 SNS 로그인 요청을 Social Login Type 을 받아 처리
     * @param socialLoginType (GOOGLE, NAVER)
     */
    @GetMapping(value = "/{socialLoginType}")
    public void socialLoginType(
            @PathVariable(name = "socialLoginType") SocialLoginType socialLoginType) {
        log.info(">> 사용자로부터 SNS 로그인 요청을 받음 :: {} Social Login", socialLoginType);
        oauthService.request(socialLoginType);
    }

    /**
     * Social Login API Server 요청에 의한 callback 을 처리
     * @param socialLoginType (GOOGLE,NAVER)
     * @param code API Server 로부터 넘어노는 code
     * @return SNS Login 요청 결과로 받은 Json 형태의 String 문자열 (access_token, refresh_token 등)
     */
    @GetMapping(value = "/{socialLoginType}/callback")
    public RedirectView callback( //ResponseEntity<String> GetSocialOAuthRes
                                       @PathVariable(name = "socialLoginType") SocialLoginType socialLoginType,
                                       @RequestParam(name = "code") String code) throws JsonProcessingException {
        log.info(">> 소셜 로그인 API 서버로부터 받은 code :: {}", code);
        RedirectView redirectView = new RedirectView();
        GetSocialOAuthRes token = oauthService.requestAccessToken(socialLoginType, code);

        redirectView.setUrl("http://43.200.92.244:8000/memo/?AccessToken="+token.getJwtAccessToken());

        return redirectView;
    }
    @GetMapping(value= "/login")
    public ResponseEntity<?> login(@RequestBody String email,HttpServletResponse response)
    {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Set-Cookie","platform=mobile; Max-Age=604800; Path=/; Secure; HttpOnly");
//        ResponseEntity.status(HttpStatus.OK).headers(headers).build();
        String token = tokenService.findRefreshtoken(email);
//        Cookie cookie =makeCookie.createCookie("refresh", token);
        response.addHeader("set-Cookie","refreshToken="+token);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}