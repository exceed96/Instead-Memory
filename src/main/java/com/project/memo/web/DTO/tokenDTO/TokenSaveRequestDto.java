package com.project.memo.web.DTO.tokenDTO;

import com.project.memo.auth.token.Token;
import com.project.memo.domain.user.Role;
import com.project.memo.domain.user.users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenSaveRequestDto {
    private String refreshToken;
    private String accessToken;
    private String email;

    @Builder
    public TokenSaveRequestDto(String refreshToken,String email,String accessToken){
        this.refreshToken = refreshToken;
        this.email = email;
        this.accessToken = accessToken;
    }

    public Token toEntity(){
        return Token.builder()
                .refreshToken(this.refreshToken)
                .accessToken(this.accessToken)
                .email(this.email)
                .build();
    }
}
