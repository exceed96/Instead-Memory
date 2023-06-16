package com.project.memo.web;


import com.project.memo.auth.jwt.JwtService;
import com.project.memo.auth.token.makeCookie;
import com.project.memo.auth.token.tokenService;
import com.project.memo.common.ResultMsg;
import com.project.memo.exception.DirSameNameException;
import com.project.memo.exception.TokenExpiredException;
import com.project.memo.service.memoService;
import com.project.memo.service.userService;
import com.project.memo.web.DTO.memoDTO.MemoResponseDto;
import com.project.memo.web.DTO.memoDTO.memoSaveRequestDto;
import com.project.memo.web.VO.memoVo;
import com.project.memo.web.VO.uuidVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.http.HttpHeaders;
import java.util.List;
import java.util.UUID;

import static com.project.memo.util.Define.MEMO_VERSION_PATH;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = MEMO_VERSION_PATH)
@CrossOrigin(origins = "https://insteadmemo.kr")
public class memoApiController {
    private final memoService memoService;
    private final JwtService jwtService;
    private final tokenService tokenService;
    @Value("${jwt.token.secret.key}")
    private String JWT_SECRET_KEY;

//    @PostMapping("/v1/memo")
    @RequestMapping(value = "/memo") //메모저장api
    public String memoSave(@RequestBody memoVo memoVo,@CookieValue("refresh") String refresh, HttpServletRequest request,HttpServletResponse response){//, @LoginUser SessionUser user
        String token = null;
        try {
            token = tokenService.checkAccessToken(request,response,refresh);
        } catch (TokenExpiredException e) {
            response.setStatus(390);
            return "390";
        }
        String email = jwtService.getUserNum(token);
//         JWT 토큰 사용하기
        memoSaveRequestDto requestDto = new memoSaveRequestDto(memoVo.getTitle(), memoVo.getContent(), email,
                memoVo.isImportant(),0, UUID.randomUUID().toString());
        memoService.save(requestDto);
        return "200";
    }
    @PutMapping(value = "/memo/update") //memo 전체 업데이트
    public void memoImportant(@RequestBody memoVo memoVo, @CookieValue("refresh") String refresh,HttpServletRequest request,HttpServletResponse response){
        try {
            tokenService.checkAccessToken(request,response,refresh);
        } catch (TokenExpiredException e) {
            response.setStatus(390);
            return ;
        }
        //uuid와 같은 메모에 대한 전체를 찾아오기
        List<MemoResponseDto> list = memoService.findMemo(memoVo.getUuid());
        //important변경값 업데이트 저장하기
        memoService.memoUpdate(memoVo.getUuid(),memoVo.getTitle(), memoVo.getContent(), memoVo.isImportant());
     }
    @GetMapping(value = "/memo/find/userInfo") //uuid에 대한 유저 정보를 한줄 찾아오는 ..
    public @ResponseBody ResultMsg<MemoResponseDto> memofindUuid(@RequestParam(value = "uuid") String uuid,@CookieValue("refresh") String refresh,HttpServletRequest request,HttpServletResponse response){
        try {
            tokenService.checkAccessToken(request,response,refresh);
        } catch (TokenExpiredException e) {
            response.setStatus(390);
            return new ResultMsg<>(false, "Token expired", null);
        }
        return new ResultMsg<MemoResponseDto>(true, "memo",memoService.findUserMemo(uuid));
    }
    @GetMapping("/memo/find") //찾아서 그 친구와 맞는 사람의 메모 return
    public @ResponseBody ResultMsg<MemoResponseDto> memoFind(@CookieValue("refresh") String refresh,HttpServletRequest request,HttpServletResponse response)//@LoginUser SessionUser user
    {
        String token = null;
        try {
            token = tokenService.checkAccessToken(request, response,refresh);
        } catch (TokenExpiredException e) {
            response.setStatus(390);
            return new ResultMsg<>(false, "Token expired", null);
        }
        // JWT 토큰 사용하기
        String email = jwtService.getUserNum(token);
        return new ResultMsg<MemoResponseDto>(true, "memo",memoService.findUser(email));
    }
    @PutMapping(value = "/memo/important") //important만 업데이트 해주는 api
    public String memoImportant(@RequestBody uuidVO uuid,@CookieValue("refresh") String refresh,HttpServletRequest request,HttpServletResponse response){
        try {
            tokenService.checkAccessToken(request,response,refresh);
        } catch (TokenExpiredException e) {
            response.setStatus(390);
            return "390";
        }
        boolean important = memoService.findMemoImportant(uuid.getUuid());
        memoService.updateImportant(uuid.getUuid(), important);
        return "200";
    }
    @DeleteMapping("/memo/delete")
    public void memoDelete(@RequestBody memoVo memoVo, @CookieValue("refresh") String refresh,HttpServletRequest request,HttpServletResponse response){
        try {
            tokenService.checkAccessToken(request,response,refresh);
        } catch (TokenExpiredException e) {
            response.setStatus(390);
            return ;
        }
        memoService.deleted(memoVo.getUuid());
    }
}
