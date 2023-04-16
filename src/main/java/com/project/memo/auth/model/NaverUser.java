package com.project.memo.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@Getter
@Data
@NoArgsConstructor
public class NaverUser {
//    public String email;
//    public String profile_image;
//    public String name;
    public String resultcode;
    public String message;
    public Map<String,String> response;
}
