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
import org.thymeleaf.util.StringUtils;

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
    private String emailCheck = null; //email있는지 없는지 체크
    private GoogleOAuthToken oAuthToken = null;
    private ResponseEntity<String> userInfoResponse = null;
    private GoogleUser googleUser = null;
    private NaverOAuthToken naverToken = null;
    private NaverUser naverUser = null;
    private TokenSaveRequestDto tokenSave = null;
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

        //응답객체가 Json형식 이를 deserialization해서 자바 객체에 담는다.
        if (socialLoginType.toString() == "GOOGLE") {
            oAuthToken = googleOauth.getAccessToken(accessTokenResponse);
            userInfoResponse = googleOauth.requeseUserInfo(oAuthToken);//액세스 토큰을 다시 구글로 보내 구글에 저장된 사용자 정보가 담긴 응답객체를 받아온다.
            googleUser = googleOauth.getUserInfo(userInfoResponse);//다시 json형식의 응답객체를 자바객체로 역직렬화
            emailCheck = googleUser.getEmail();
        }
        else if (socialLoginType.toString().equals("NAVER")) {
            naverToken = naverOauth.getAccessToken(accessTokenResponse);
            userInfoResponse = naverOauth.requeseUserInfo(naverToken);
            naverUser = naverOauth.getUserInfo(userInfoResponse);
            emailCheck=naverUser.getResponse().get("email");
        }
        if(emailCheck!=""){
            //서버에 user가 존재하면 앞으로 회원 인가 처리를 위한 jwtToken을 발급한다.
            GetSocialOAuthRes getSocialOAuthRes = tokenIssueSave(socialLoginType);
            return getSocialOAuthRes;
        }
        return null;
    }
    private GetSocialOAuthRes tokenIssueSave(SocialLoginType socialLoginType)
    {
        if (socialLoginType.toString().equals("GOOGLE")) {
            Token jwtToken = jwtService.createJwt(emailCheck, googleUser.getName());

            boolean check = getNameCheck(googleUser.getEmail());
            checkSave(check,jwtToken,"google"); //check시 false라면 토큰도 저장하고 유저도 저장함

            //액세스 토큰과 jwtToken, 이외 정보들이 담긴 자바 객체를 다시 전송한다.
            GetSocialOAuthRes getSocialOAuthRes = loginRefreshCheck(jwtToken,"google");

            return getSocialOAuthRes;
        }else if (socialLoginType.toString().equals("NAVER"))
        {
            Token jwtToken = jwtService.createJwt(emailCheck, naverUser.getResponse().get("name"));
            boolean check = getNameCheck(naverUser.getResponse().get("email"));
            checkSave(check,jwtToken,"naver"); //check시 false라면 토큰도 저장하고 유저도 저장함

            //액세스 토큰과 jwtToken, 이외 정보들이 담긴 자바 객체를 다시 전송한다.
            GetSocialOAuthRes getSocialOAuthRes = loginRefreshCheck(jwtToken,"naver");

            return getSocialOAuthRes;
        }
        return null;
    }
    private GetSocialOAuthRes loginRefreshCheck(Token jwtToken,String social)
    {
        String refresh = tokenService.findRefreshtoken(emailCheck); //user_id와 같은 refreshToken찾아옴

        if (StringUtils.isEmpty(refresh)) { //false 새롭게 들어오는거 싹다 저장해서 리턴
             //refreshToken이 만료되지않았는지 체크해줌!!
            int user_id = userService.user_key(jwtToken.getEmail());
            tokenSave = new TokenSaveRequestDto(jwtToken.getRefreshToken(),jwtToken.getEmail(),jwtToken.getAccessToken(),user_id);
            tokenService.save(tokenSave);
            refresh = jwtToken.getRefreshToken();
        }
        else if(!jwtService.validateToken(refresh) && !StringUtils.isEmpty(refresh)) { //false이면서 refreshToken이 이미 저장되어있는거
            //jwt 업데이트 해주깅
            tokenService.updateJWT(jwtToken.getRefreshToken(), jwtToken.getAccessToken(),jwtToken.getEmail());
            refresh = jwtToken.getRefreshToken();
        }
        GetSocialOAuthRes getSocialOAuthRes = null;
        if (social.equals("google")){
            getSocialOAuthRes = new GetSocialOAuthRes(googleUser.getName(), jwtToken.getAccessToken(), refresh, oAuthToken.getToken_type());
        }else {
            getSocialOAuthRes = new GetSocialOAuthRes(naverUser.getResponse().get("name"), jwtToken.getAccessToken(), refresh,naverToken.getToken_type());
        }
        return getSocialOAuthRes;
    }
    private void checkSave(boolean check,Token jwtToken,String social)
    {
        if (check) {
            /* user 저장된게 없을때 저장해주기 */
            if (social.equals("google")) {
                userSaveRequestDto = new UserSaveRequestDto(googleUser.getName(),
                        googleUser.getEmail(), googleUser.getPicture());
                userService.save(userSaveRequestDto);
            }else { //네이버 일때
                userSaveRequestDto = new UserSaveRequestDto(naverUser.getResponse().get("name"),
                        naverUser.getResponse().get("email"),
                        naverUser.getResponse().get("profile_image"));
                userService.save(userSaveRequestDto);
            }
            int user_id = userService.user_key(jwtToken.getEmail());
            tokenSave = new TokenSaveRequestDto(jwtToken.getRefreshToken(),jwtToken.getEmail(),jwtToken.getAccessToken(),user_id);
            tokenService.save(tokenSave);
        }
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