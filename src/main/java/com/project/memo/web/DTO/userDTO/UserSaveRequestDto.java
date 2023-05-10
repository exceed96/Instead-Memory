package com.project.memo.web.DTO.userDTO;

import com.project.memo.domain.memoContent.memoContent;
import com.project.memo.domain.user.Role;
import com.project.memo.domain.user.users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
public class UserSaveRequestDto {

    private String name;

    private String email;

    private String picture;

    private Role role;

    @Builder
    public UserSaveRequestDto(String name,String email,String picture){
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public users toEntity(){
        return users.builder()
                .name(this.name)
                .picture(this.picture)
                .email(this.email)
                .role(Role.USER)
                .build();
    }
}
