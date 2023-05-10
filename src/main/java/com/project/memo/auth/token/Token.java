package com.project.memo.auth.token;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String refreshToken;
    private String accessToken;
    private String email;
    @Temporal(TemporalType.TIMESTAMP)
    private Date recentLogin;
    @Builder
    public Token(String email, String refreshToken, String accessToken)
    {
        this.email = email;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.recentLogin = new Date();
    }
}
