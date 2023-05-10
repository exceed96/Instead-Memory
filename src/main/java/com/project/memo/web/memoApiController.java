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
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://43.200.92.244:8000")
public class memoApiController {
    private final memoService memoService;
    private final JwtService jwtService;
    @Value("${jwt.token.secret.key}")
    private String JWT_SECRET_KEY;

//    @PostMapping("/v1/memo")
    @RequestMapping(value = "/v1/memo") //처음 저장하는 api
    public boolean memoSave(@RequestBody memoVo memoVo,HttpServletRequest request){//, @LoginUser SessionUser user
        memoSaveRequestDto requestDto;
        String title = memoVo.getTitle();
        String content = memoVo.getContent();

        String jwtToken = request.getHeader("Authorization");
        // JWT 토큰 사용하기
        String email = jwtService.getUserNum(jwtToken);
        System.out.println("여기는 메모저장 이메일 입니다. " +email);
        String id = UUID.randomUUID().toString(); // idx(int)로 구분하지 않고 랜덤 해시값을 통해 메모 구분
        int important = memoVo.getImportant();
//        int bookMark = memoVo.getBookMark();
        requestDto = new memoSaveRequestDto(title,content,email, important,0,id);
        memoService.save(requestDto);
        return true;
    }
    @PutMapping(value = "/v1/memo/update") //important 하는 api
    public boolean memoImportant(@RequestBody memoVo memoVo, HttpServletRequest request){
        String jwtToken = request.getHeader("Authorization");
        int important = memoVo.getImportant();
        String title = memoVo.getTitle();
        String content = memoVo.getContent();
        String uuid = memoVo.getUuid();

        //uuid와 같은 메모에 대한 전체를 찾아오기
        List<MemoResponseDto> list = memoService.findMemo(uuid);
        //important변경값 업데이트 저장하기
        memoService.memoUpdate(uuid,title, content, important);
        return true;
    }
    @GetMapping("/v1/memo/find") //찾아서 그 친구와 맞는 사람의 메모 return
    public @ResponseBody ResultMsg<MemoResponseDto> memoFind(HttpServletRequest request)//@LoginUser SessionUser user
    {
        String jwtToken = request.getHeader("Authorization");
        // JWT 토큰 사용하기
        String email = jwtService.getUserNum(jwtToken);
        System.out.println("여기는 메모find 이메일 입니다. " +email);
        //!! email값에 아직 아무 메모내용이없을때 에러뜨는거 수정해야함
        return new ResultMsg<MemoResponseDto>(true, "memo",memoService.findUser(email));
    }
    @DeleteMapping("/v1/memo/delete/{uuid}")
    public void memoDelete(@PathVariable("uuid") String uuid){
        System.out.println(uuid);
        memoService.deleted(uuid);
    }
}
