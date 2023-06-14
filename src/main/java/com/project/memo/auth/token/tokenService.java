package com.project.memo.auth.token;

import com.project.memo.auth.jwt.JwtService;
import com.project.memo.exception.TokenExpiredException;
import com.project.memo.service.userService;
import com.project.memo.web.DTO.memoDTO.memoSaveRequestDto;
import com.project.memo.web.DTO.tokenDTO.TokenSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
//    @Transactional
//    public String findRefreshtoken(String email) {
//        System.out.println("eeee:" +tokenRepository.findRefreshToken(email));
//        return tokenRepository.findRefreshToken(email);
//    }
    @Transactional
    public String getfindemail(String refresh){
        return tokenRepository.getFindEmail(refresh);
    }
    @Transactional
    public void deleted(String email) {
        tokenRepository.tokendelete(email);
    }
    public void checkRefreshToken(HttpServletResponse response){

    }
    public String checkAccessToken(HttpServletRequest request, HttpServletResponse response)
    {
        String jwtToken = request.getHeader("Authorization");
        boolean check = jwtService.validateToken(jwtToken.replace("Bearer",""));
        if (!check){
            //refreshtoken 을 이용햐여 token table 에서 이메일 받아오기
            String email = getfindemail(request.getHeader("Cookie").replace("refresh=", ""));
            //이 이메일을 가진 사람의 이름을 가지고 와서 accessToken 만들어서 리턴해주기!
            String user = userService.findUserName(email);
            String accessToken = jwtService.createAccessToken(email, user);
            response.addHeader("Authorization", "Bearer " + accessToken);
            response.setStatus(605);
            throw new TokenExpiredException();
        }
        return jwtToken;
    }
}
