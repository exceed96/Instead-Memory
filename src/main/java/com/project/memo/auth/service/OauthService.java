package com.project.memo.auth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.memo.auth.model.*;
import com.project.memo.auth.jwt.JwtService;
import com.project.memo.auth.type.SocialLoginType;
import com.project.memo.service.userService;
import com.project.memo.web.DTO.userDTO.UserSaveRequestDto;
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
    private final NaverOauth naverOauth;
    UserSaveRequestDto userSaveRequestDto;
    private final userService userService;
    private final JwtService jwtService;
    public void request(SocialLoginType socialLoginType) {
        SocialOauth socialOauth = this.findSocialOauthByType(socialLoginType);
        String redirectURL = socialOauth.getOauthRedirectURL();
        try {
            response.sendRedirect(redirectURL);
            System.out.println(redirectURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public GetSocialOAuthRes requestAccessToken(SocialLoginType socialLoginType, String code) throws JsonProcessingException {//ResponseEntity<String>
        SocialOauth socialOauth = this.findSocialOauthByType(socialLoginType);
        //구글로 일회성 코드를 보내 액세스 토큰이 담긴 응답객체를 받아옴
        ResponseEntity<String> accessTokenResponse = socialOauth.requestAccessToken(code);
        String user_id = null; //email있는지 없는지 체크

        GoogleOAuthToken oAuthToken = null;
        ResponseEntity<String> userInfoResponse = null;
        GoogleUser googleUser = null;

        NaverOAuthToken naverToken = null;
        NaverUser naverUser = null;
        //응답객체가 Json형식 이를 deserialization해서 자바 객체에 담는다.
        if (socialLoginType.toString() == "GOOGLE") {
            oAuthToken = googleOauth.getAccessToken(accessTokenResponse);
            userInfoResponse = googleOauth.requeseUserInfo(oAuthToken);//액세스 토큰을 다시 구글로 보내 구글에 저장된 사용자 정보가 담긴 응답객체를 받아온다.
            googleUser = googleOauth.getUserInfo(userInfoResponse);//다시 json형식의 응답객체를 자바객체로 역직렬화
            user_id = googleUser.getEmail();
        }
        else if (socialLoginType.toString().equals("NAVER")) {
            naverToken = naverOauth.getAccessToken(accessTokenResponse);
            userInfoResponse = naverOauth.requeseUserInfo(naverToken);
            naverUser = naverOauth.getUserInfo(userInfoResponse);
            user_id=naverUser.getResponse().get("email");
        }
//        System.out.println(googleUser.name+" " + googleUser.getEmail() + " " + googleUser.picture);
//        userSaveRequestDto = new UserSaveRequestDto(googleUser.name, "20162330@vision.hoseo.edu", googleUser.picture);// googleUser.email,
//        userService.save(userSaveRequestDto);

        if(user_id!=""){
            //서버에 user가 존재하면 앞으로 회원 인가 처리를 위한 jwtToken을 발급한다.
            if (socialLoginType.toString().equals("GOOGLE")) {
                String jwtToken = jwtService.createJwt(user_id, googleUser.getName());
                //액세스 토큰과 jwtToken, 이외 정보들이 담긴 자바 객체를 다시 전송한다.
                GetSocialOAuthRes getSocialOAuthRes = new GetSocialOAuthRes(googleUser.getName(), jwtToken, oAuthToken.getAccess_token(), oAuthToken.getToken_type());
                boolean check = getNameCheck(googleUser.getEmail());
                System.out.println(check);
                if (check) {
                    System.out.println("a");
                    userSaveRequestDto = new UserSaveRequestDto(googleUser.getName(), googleUser.getEmail(), googleUser.getPicture(), getSocialOAuthRes.getJwtToken());
                    userService.save(userSaveRequestDto);
                }
                return getSocialOAuthRes;
            }
            else if (socialLoginType.toString().equals("NAVER"))
            {
                String jwtToken = jwtService.createJwt(user_id, naverUser.getResponse().get("name"));
                //액세스 토큰과 jwtToken, 이외 정보들이 담긴 자바 객체를 다시 전송한다.
                GetSocialOAuthRes getSocialOAuthRes = new GetSocialOAuthRes(naverUser.getResponse().get("name"), jwtToken, naverToken.getAccess_token(), naverToken.getToken_type());
                boolean check = getNameCheck(naverUser.getResponse().get("email"));
                if (check) {
                    userSaveRequestDto = new UserSaveRequestDto(naverUser.getResponse().get("name"),
                            naverUser.getResponse().get("email"),
                            naverUser.getResponse().get("profile_image"),
                            getSocialOAuthRes.getJwtToken());
                    userService.save(userSaveRequestDto);
                }
                return getSocialOAuthRes;
            }
        }
        return null;
    }

    private SocialOauth findSocialOauthByType(SocialLoginType socialLoginType) {
        return socialOauthList.stream()
                .filter(x -> x.type() == socialLoginType)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("알 수 없는 SocialLoginType 입니다."));
    }
    private boolean getNameCheck(String email)
    {
        String emailCheck = userService.findUserEmail(email);
        System.out.println(emailCheck);
        if (emailCheck == null)
            return true;
        return false;
    }
}