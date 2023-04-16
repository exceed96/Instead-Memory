package com.project.memo.auth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.memo.auth.model.GoogleOAuthToken;
import com.project.memo.auth.model.GoogleUser;
import com.project.memo.auth.model.NaverOAuthToken;
import com.project.memo.auth.model.NaverUser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Getter
public class NaverOauth implements SocialOauth {
    @Value("${sns.naver.authorization-uri}")
    private String NAVER_SNS_BASE_URL;
    @Value("${sns.naver.client-id}")
    private String NAVER_SNS_CLIENT_ID;
    @Value("${sns.naver.callback.url}")
    private String NAVER_SNS_CALLBACK_URL;
    @Value("${sns.naver.client-secret}")
    private String NAVER_SNS_CLIENT_SECRET;
    @Value("${sns.naver.token-uri}")
    private String NAVER_SNS_TOKEN_BASE_URL;

    private RestTemplate restTemplate = new RestTemplate();
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String getOauthRedirectURL() {
        Map<String, Object> params = new HashMap<>();
//        params.put("scope","profile+email");
        params.put("response_type", "code");
        params.put("state", "state");
        params.put("client_id", "BQEax4ZcNgO3kZKUentg");
        params.put("redirect_uri", NAVER_SNS_CALLBACK_URL);

        String parameterString = params.entrySet().stream()
                .map(x -> x.getKey() + "=" + x.getValue())
                .collect(Collectors.joining("&"));

        return NAVER_SNS_BASE_URL + "?" + parameterString;
    }

    @Override
    public ResponseEntity<String> requestAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> param = new HashMap<>();
        param.put("code", code);
        param.put("state", "state");
        param.put("error","error");
        param.put("error_description", "error_description");
        param.put("client_id", "BQEax4ZcNgO3kZKUentg");
        param.put("client_secret", "NQkVkAZGlQ");
        param.put("redirect_uri", NAVER_SNS_CALLBACK_URL);
        param.put("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
//        RestTemplate restTemplate = new RestTemplate();

//        Map<String, Object> params = new HashMap<>();
        params.add("code", code);
        params.add("state", "state");
        params.add("error","error");
        params.add("error_description", "error_description");
        params.add("client_id", NAVER_SNS_CLIENT_ID);
        params.add("client_secret", NAVER_SNS_CLIENT_SECRET);
        params.add("redirect_uri", NAVER_SNS_CALLBACK_URL);
        params.add("grant_type", "authorization_code");
        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity(NAVER_SNS_TOKEN_BASE_URL, params, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            System.out.println(">> status Code : " + responseEntity.getStatusCode());
            return responseEntity;
        }
        return null; //"구글 로그인 요청 처리 실패";
    }

    public String requestAccessTokenUsingURL(String code) {
        try {
            URL url = new URL(NAVER_SNS_TOKEN_BASE_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);

            Map<String, Object> params = new HashMap<>();
            params.put("code", code);
            params.put("state", URLEncoder.encode("1234", "UTF-8"));
            params.put("error","error");
            params.put("error_description", "error_description");
//            params.put("client_id", NAVER_SNS_CLIENT_ID);
//            params.put("client_secret", NAVER_SNS_CLIENT_SECRET);
//            params.put("redirect_uri", NAVER_SNS_CALLBACK_URL);
//            params.put("grant_type", "authorization_code");

            String parameterString = params.entrySet().stream()
                    .map(x -> x.getKey() + "=" + x.getValue())
                    .collect(Collectors.joining("&"));

            BufferedOutputStream bous = new BufferedOutputStream(conn.getOutputStream());
            bous.write(parameterString.getBytes());
            bous.flush();
            bous.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            if (conn.getResponseCode() == 200) {
                return sb.toString();
            }
            return "네이버 로그인 요청 처리 실패";
        } catch (IOException e) {
            throw new IllegalArgumentException("알 수 없는 네이버 로그인 Access Token 요청 URL 입니다 :: " + NAVER_SNS_TOKEN_BASE_URL);
        }
    }
    public NaverOAuthToken getAccessToken(ResponseEntity<String> response) throws JsonProcessingException {
        System.out.println("response.getBody() = " + response.getBody());
        NaverOAuthToken naverOAuthToken = objectMapper.readValue(response.getBody(),NaverOAuthToken.class);
        return naverOAuthToken;
    }

    public ResponseEntity<String> requeseUserInfo(NaverOAuthToken oAuthToken){
        String NAVER_USERINFO_REQUEST_URL="https://openapi.naver.com/v1/nid/me";

        //header에 accessToken을 담는다.
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + oAuthToken.getAccess_token());
        //HttpEntity를 하나 생성해 헤더를 담아서 restTemplate으로 구글과 통신하게 된다.
        HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(NAVER_USERINFO_REQUEST_URL, HttpMethod.GET, request,String.class);
        System.out.println("response.getBody() = " + response.getBody());
        return response;
    }
    public NaverUser getUserInfo(ResponseEntity<String> userInfoRes) throws JsonProcessingException{
        NaverUser naverUser = objectMapper.readValue(userInfoRes.getBody(),NaverUser.class);
        return naverUser;
    }
}
