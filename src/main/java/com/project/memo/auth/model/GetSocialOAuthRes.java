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
    private String jwtAccessToken;
    private String jwtRefreshToken;
    private String tokenType;

}
