package com.project.memo.auth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class GoogleUser {
    public String id;
    public String email;
    public Boolean verifiedEmail;
    public String name;
    public String given_name;
    public String family_name;
    public String picture;
    public String locale;
}
