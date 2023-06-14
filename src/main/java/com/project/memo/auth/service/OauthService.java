package com.project.memo.auth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.memo.auth.model.*;
import com.project.memo.auth.jwt.JwtService;
import com.project.memo.auth.token.Token;
import com.project.memo.auth.token.tokenService;
import com.project.memo.auth.type.SocialLoginType;
import com.project.memo.service.userService;
import com.project.memo.web.DTO.tokenDTO.TokenSaveRequestDto;
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
    private final tokenService tokenService;
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

        TokenSaveRequestDto tokenSave = null;
        //응답객체가 Json형식 이를 deserialization해서 자바 객체에 담는다.
        if (socialLoginType.toString() == "GOOGLE") {
            oAuthToken = googleOauth.getAccessToken(accessTokenResponse);
            System.out.println("google login 액세스 토큰 " + oAuthToken.getAccess_token());
            System.out.println("google login 리프레시 토큰 " + oAuthToken.getRefresh_token());

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

        if(user_id!=""){
            //서버에 user가 존재하면 앞으로 회원 인가 처리를 위한 jwtToken을 발급한다.
            if (socialLoginType.toString().equals("GOOGLE")) {
                Token jwtToken = jwtService.createJwt(user_id, googleUser.getName());
//                System.out.println("jwtToken 에서 액세스 토큰 " + jwtToken.getAccessToyken());
//                System.out.println("jwtToken 에서 리프레시 토큰 " + jwtToken.getRefreshToken());

                //액세스 토큰과 jwtToken, 이외 정보들이 담긴 자바 객체를 다시 전송한다.
                GetSocialOAuthRes getSocialOAuthRes = new GetSocialOAuthRes(googleUser.getName(), jwtToken.getAccessToken(),
                        jwtToken.getRefreshToken(), oAuthToken.getToken_type());
                boolean check = getNameCheck(googleUser.getEmail());
                System.out.println(check);
                if (check) { //user가 저장되어있지않을때 저장하게 해줌
                    /* 액세슽 토큰 리프레시 토큰 이메일 저장하기 */
                    //tokenSave는 user가 저장되어있지않을때만 들어옴 => 로그아웃하면 user에 대한걸 삭제해서 괜찮나?
                    tokenSave = new TokenSaveRequestDto(jwtToken.getRefreshToken(),jwtToken.getEmail(),jwtToken.getAccessToken());
                    tokenService.save(tokenSave);
                    /* user 저장된게 없을때 저장해주기 */
                    userSaveRequestDto = new UserSaveRequestDto(googleUser.getName(),
                            googleUser.getEmail(), googleUser.getPicture());
                    userService.save(userSaveRequestDto);
                }
                //있으면 삭제
                tokenService.deleted(jwtToken.getEmail());
                tokenSave = new TokenSaveRequestDto(jwtToken.getRefreshToken(),jwtToken.getEmail(),jwtToken.getAccessToken());
                tokenService.save(tokenSave);
                return getSocialOAuthRes;
            }
            else if (socialLoginType.toString().equals("NAVER"))
            {
                Token jwtToken = jwtService.createJwt(user_id, naverUser.getResponse().get("name"));
                //액세스 토큰과 jwtToken, 이외 정보들이 담긴 자바 객체를 다시 전송한다.
                GetSocialOAuthRes getSocialOAuthRes = new GetSocialOAuthRes(naverUser.getResponse().get("name"), jwtToken.getAccessToken(),
                        jwtToken.getRefreshToken(),naverToken.getToken_type());
                boolean check = getNameCheck(naverUser.getResponse().get("email"));
                if (check) {
                    /* 액세슽 토큰 리프레시 토큰 이메일 저장하기 */
                    tokenSave = new TokenSaveRequestDto(jwtToken.getRefreshToken(),jwtToken.getEmail(),jwtToken.getAccessToken());
                    tokenService.save(tokenSave);
                    /* user 저장된게 없을때 저장해주기 */
                    userSaveRequestDto = new UserSaveRequestDto(naverUser.getResponse().get("name"),
                            naverUser.getResponse().get("email"),
                            naverUser.getResponse().get("profile_image"));
                    userService.save(userSaveRequestDto);
                }
                //있으면 삭제
                tokenService.deleted(jwtToken.getEmail());
                tokenSave = new TokenSaveRequestDto(jwtToken.getRefreshToken(),jwtToken.getEmail(),jwtToken.getAccessToken());
                tokenService.save(tokenSave);
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