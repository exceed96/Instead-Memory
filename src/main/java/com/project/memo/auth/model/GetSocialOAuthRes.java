package com.project.memo.auth.model;

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
//    private int user_num;
    private String accessToken;
    private String tokenType;
}
