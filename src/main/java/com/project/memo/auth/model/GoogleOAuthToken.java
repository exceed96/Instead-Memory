package com.project.memo.auth.model;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Data
@NoArgsConstructor
public class GoogleOAuthToken {
    private String access_token;
    private int expires_in;
    private String scope;
    private String token_type;
    private String id_token;
}
