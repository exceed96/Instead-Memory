package com.project.memo.auth.model;

import lombok.*;

@AllArgsConstructor
@Getter
@Data
@NoArgsConstructor
public class GoogleUser {
    public String id;
    public String email;
    public boolean verified_email;
    public String name;
    public String given_name;
    public String family_name;
    public String picture;
    public String locale;
    public String hd;
}
