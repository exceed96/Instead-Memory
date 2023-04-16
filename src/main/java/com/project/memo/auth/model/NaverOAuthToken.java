package com.project.memo.auth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class NaverOAuthToken {
    private String access_token;
//    private String scope;
    private int expires_in;
    private String state;
    private String refresh_token;
    private String token_type;
    private String id_token;
    private String error;
    private String error_description;
}
