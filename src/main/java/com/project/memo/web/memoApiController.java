package com.project.memo.web;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.memo.auth.jwt.JwtService;
import com.project.memo.common.ResultMsg;
import com.project.memo.service.memoService;
import com.project.memo.web.DTO.memoDTO.MemoResponseDto;
import com.project.memo.web.DTO.memoDTO.memoSaveRequestDto;
import com.project.memo.web.VO.memoVo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpHeaders;
import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://43.200.92.244:8000")
public class memoApiController {
    private final memoService memoService;
    private final JwtService jwtService;
    @Value("${jwt.token.secret.key}")
    private String JWT_SECRET_KEY;

//    @PostMapping("/v1/memo")
    @RequestMapping(value = "/v1/memo")
    public boolean memoSave(@RequestBody memoVo memoVo,HttpServletRequest request){//, @LoginUser SessionUser user
        memoSaveRequestDto requestDto;
        String title = memoVo.getTitle();
        String content = memoVo.getContent();
        String jwtToken = request.getHeader("Authorization");
        // JWT 토큰 사용하기
        String email = jwtService.getUserNum(jwtToken);
        System.out.println("여기는 메모저장 이메일 입니다. " +email);
        int max = memoService.findMaxIndex(email);
//        int importante = memoVo.getImportante();
//        int bookMark = memoVo.getBookMark();
        requestDto = new memoSaveRequestDto(title,content,email, 0,0);
        memoService.save(requestDto);
        return true;
    }
    @GetMapping("/v1/memo/find")
    public @ResponseBody ResultMsg<MemoResponseDto> memoFind(HttpServletRequest request)//@LoginUser SessionUser user
    {
        String jwtToken = request.getHeader("Authorization");
        // JWT 토큰 사용하기
        String email = jwtService.getUserNum(jwtToken);
        System.out.println("여기는 메모find 이메일 입니다. " +email);

        return new ResultMsg<MemoResponseDto>(true, "memo",memoService.findUser(email));
    }
    @DeleteMapping("/v1/memo/delete/{idx}")
    public void memoDelete(@PathVariable("idx") int idx){
        System.out.println(idx);
        memoService.deleted(idx);
    }
}
