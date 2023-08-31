package com.project.memo.auth.token;

import com.project.memo.auth.jwt.JwtService;
import com.project.memo.exception.RefreshExpiredException;
import com.project.memo.exception.TokenExpiredException;
import com.project.memo.service.userService;
import com.project.memo.web.DTO.memoDTO.memoSaveRequestDto;
import com.project.memo.web.DTO.tokenDTO.TokenSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Service
public class tokenService {
    private final userService userService;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    @Transactional
    public void save(TokenSaveRequestDto requestDto){
        tokenRepository.save(requestDto.toEntity()).getId(); // 저장
    }
    /*안써서 주석처리 나중에 지울수 있음*/
    @Transactional
    public String findRefreshtoken(String email) {
        return tokenRepository.findRefreshToken(email);
    }
    @Transactional
    public String getfindemail(String refresh){
        return tokenRepository.getFindEmail(refresh);
    }
//    @Transactional
//    public void deleted(String email) {
//        tokenRepository.tokendelete(email);
//    }
    @Transactional
    public String findRefresh(String refresh){return tokenRepository.getFindEmail(refresh);}
    public String checkAccessToken(HttpServletRequest request, HttpServletResponse response,String refresh)
    {
        String jwtToken = request.getHeader("Authorization");
        try{
            boolean check = jwtService.validateToken(jwtToken.replace("Bearer",""));
            if (!check){
                // arp_scroll_position=0;
                //refreshtoken 을 이용햐여 token table 에서 이메일 받아오기
                String email = getfindemail(refresh);
                //이 이메일을 가진 사람의 이름을 가지고 와서 accessToken 만들어서 리턴해주기!
                String user = userService.findUserName(email);
                String accessToken = jwtService.createAccessToken(email, user);
                System.out.println(email +" : " + user);
                response.addHeader("Authorization", "Bearer " + accessToken);
                throw new TokenExpiredException();
            }
        }catch(TokenExpiredException e) {
            response.setStatus(390);
            throw e;
        }
        return jwtToken;
    }
    public void checkRefreshToken(HttpServletResponse response,String refresh)
    {
        boolean check = jwtService.validateToken(refresh);
        String email = findRefresh(refresh);//리프레스 토큰 token table이랑 같은지 확인 email이 있으면 refesh 가튼지
        try{
            if (!check)
                throw new RefreshExpiredException();
        }catch(RefreshExpiredException e2) {
            response.setStatus(392);
            throw e2;
        }
    }
    @Transactional
    public void updateJWT(String refresh, String access,String email) {
        tokenRepository.updateJWT(refresh,access,email);
    }
}
