package com.project.memo.auth.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.memo.auth.model.GetSocialOAuthRes;
import com.project.memo.auth.service.OauthService;
import com.project.memo.auth.token.makeCookie;
import com.project.memo.auth.token.tokenService;
import com.project.memo.auth.type.SocialLoginType;
import com.project.memo.web.VO.uuidVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import static com.project.memo.util.Define.INSTEADMEMO_LOGIN_PATH;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://43.200.92.244:8000")
@RequestMapping(value = INSTEADMEMO_LOGIN_PATH)
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
                                  HttpServletResponse response,
                                       @PathVariable(name = "socialLoginType") SocialLoginType socialLoginType,
                                       @RequestParam(name = "code") String code) throws JsonProcessingException {
        log.info(">> 소셜 로그인 API 서버로부터 받은 code :: {}", code);
        RedirectView redirectView = new RedirectView();
        GetSocialOAuthRes token = oauthService.requestAccessToken(socialLoginType, code);

        ResponseCookie cookie =makeCookie.createCookie("refresh", token.getJwtRefreshToken());
        response.addHeader("Set-Cookie", cookie.toString());
        redirectView.setUrl("https://insteadmemo.kr/memo/?AccessToken="+token.getJwtAccessToken());
        return redirectView;
    }
//    @PostMapping(value= "/login")
//    public ResponseEntity<?> login(@RequestBody uuidVO uuid, HttpServletResponse response)
//    {
//        String token = tokenService.findRefreshtoken(uuid.getEmail());
//        System.out.println("token " + token);
//        ResponseCookie cookie =makeCookie.createCookie("refresh", token);
//        response.addHeader("Set-Cookie", cookie.toString());
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}