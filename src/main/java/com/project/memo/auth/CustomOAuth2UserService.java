package com.project.memo.auth;

import com.project.memo.auth.dto.OAuthAttributes;
import com.project.memo.auth.dto.SessionUser;
import com.project.memo.domain.user.users;
import com.project.memo.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest
                .getClientRegistration().getRegistrationId(); //현재 로그인 진행중인 서비스 구분하는 아이디
        String userNameAttributeName = userRequest
                .getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName(); //OAuth2로그인 진행시 키가되는 필드값 primary key느낌

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName,
                oAuth2User.getAttributes()); //OAuth2UserService를 통해가져온 OAuth2User의 attribbut를 담을 클래스

        users user = saveOrUpdate(attributes);

        httpSession.setAttribute("user", new SessionUser(user)); //4

        return new DefaultOAuth2User(
                Collections.singleton(new
                        SimpleGrantedAuthority((user.getRoleKey()))),
                attributes.getAttributes(),
                attributes.getNameAttributeKey()
        );
    }

    private users saveOrUpdate (OAuthAttributes attributes) {
        users user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());
        return userRepository.save(user);
    }
}