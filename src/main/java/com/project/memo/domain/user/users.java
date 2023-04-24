package com.project.memo.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column
    private String tokenJwt;
    @Builder
    public users(String name, String email, String picture, Role role,String tokenJwt) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
        this.tokenJwt = tokenJwt;
    }

    public users update(String name, String picture) {
        this.name = name;
        this.picture = picture;

        return this;
    }
    public String getRoleKey() {
        return  this.role.getKey();
    }
}