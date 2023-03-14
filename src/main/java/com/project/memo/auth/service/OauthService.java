package com.project.memo.auth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.memo.auth.model.GoogleOAuthToken;
import com.project.memo.auth.model.GoogleUser;
import com.project.memo.auth.type.SocialLoginType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OauthService {
    private final List<SocialOauth> socialOauthList;
    private final HttpServletResponse response;
    private final GoogleOauth googleOauth;

    public void request(SocialLoginType socialLoginType) {
        SocialOauth socialOauth = this.findSocialOauthByType(socialLoginType);
        String redirectURL = socialOauth.getOauthRedirectURL();
        try {
            response.sendRedirect(redirectURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ResponseEntity<String> requestAccessToken(SocialLoginType socialLoginType, String code) throws JsonProcessingException {
        SocialOauth socialOauth = this.findSocialOauthByType(socialLoginType);
        //구글로 일회성 코드를 보내 액세스 토큰이 담긴 응답객체를 받아옴
        ResponseEntity<String> accessTokenResponse = socialOauth.requestAccessToken(code);
        //응답객체가 Json형식 이를 deserialization해서 자바 객체에 담는다.
        GoogleOAuthToken oAuthToken = googleOauth.getAccessToken(accessTokenResponse);

        //액세스 토큰을 다시 구글로 보내 구글에 저장된 사용자 정보가 담긴 응답객체를 받아온다.
        ResponseEntity<String> userInfoResponse = googleOauth.requeseUserInfo(oAuthToken);
        //다시 json형식의 응답객체를 자바객체로 역직렬화
        GoogleUser googleUser = googleOauth.getUserInfo(userInfoResponse);
        System.out.println("googleUser" + googleUser.getName());
        return accessTokenResponse;
    }

    private SocialOauth findSocialOauthByType(SocialLoginType socialLoginType) {
        return socialOauthList.stream()
                .filter(x -> x.type() == socialLoginType)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("알 수 없는 SocialLoginType 입니다."));
    }
}