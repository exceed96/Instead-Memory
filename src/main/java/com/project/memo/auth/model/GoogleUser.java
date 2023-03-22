package com.project.memo.auth.model;

import lombok.*;

@AllArgsConstructor
@Getter
@Data
@NoArgsConstructor
public class GoogleUser {
    public String id;
    public String email;
    public String name;
    public String picture;
    public String locale;
}
