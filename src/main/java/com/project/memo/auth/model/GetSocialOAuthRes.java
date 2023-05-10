package com.project.memo.auth.model;

import com.project.memo.auth.token.Token;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class GetSocialOAuthRes {
    private String username;
    private String jwtToken;
    private String refreshToken;
    private String accessToken;
    private String tokenType;

}
