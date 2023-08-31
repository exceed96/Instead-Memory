package com.project.memo.web;


import com.project.memo.auth.jwt.JwtService;
import com.project.memo.auth.token.makeCookie;
import com.project.memo.auth.token.tokenService;
import com.project.memo.common.ResultMsg;
import com.project.memo.domain.BaseTimeEntity;
import com.project.memo.exception.DirSameNameException;
import com.project.memo.exception.RefreshExpiredException;
import com.project.memo.exception.TokenExpiredException;
import com.project.memo.service.memoService;
import com.project.memo.service.userService;
import com.project.memo.web.DTO.memoDTO.MemoResponseDto;
import com.project.memo.web.DTO.memoDTO.memoSaveRequestDto;
import com.project.memo.web.VO.memoVo;
import com.project.memo.web.VO.uuidVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.http.HttpHeaders;
import java.time.LocalDateTime;
import java.util.*;

import static com.project.memo.util.Define.MEMO_VERSION_PATH;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = MEMO_VERSION_PATH)
@CrossOrigin(origins = "https://insteadmemo.kr")
public class memoApiController {
    private final memoService memoService;
    private final JwtService jwtService;
    private final tokenService tokenService;
    private final userService userService;
    @Value("${jwt.token.secret.key}")
    private String JWT_SECRET_KEY;

    @ApiOperation(value = "메모 첫 저장")
    @RequestMapping(value = "/memo") //메모저장api
    public void memoSave(@RequestBody memoVo memoVo,@CookieValue("refresh") String refresh, HttpServletRequest request,HttpServletResponse response){//, @LoginUser SessionUser user
        String token = null;
        try {
            tokenService.checkRefreshToken(response,refresh);
            token = tokenService.checkAccessToken(request,response,refresh);
        }catch (RefreshExpiredException e2){
            response.setStatus(392);
            return ;
        } catch (TokenExpiredException e) {
            response.setStatus(390);
            return ;
        }
        String email = jwtService.getUserNum(token);
        int user_id = userService.user_key(email); //외래키설정에 넣어주기위한 user테이블에서 idx키를 찾아와서 memoContent에 저장

//         JWT 토큰 사용하기
        memoSaveRequestDto requestDto = new memoSaveRequestDto(memoVo.getTitle(), memoVo.getContent(), email,
                memoVo.isImportant(),0, UUID.randomUUID().toString(),user_id, LocalDateTime.now());
        memoService.save(requestDto);
    }
    @ApiOperation(value = "메모 업데이트")
    @PutMapping(value = "/memo/update") //memo 전체 업데이트
    public void memoImportant(@RequestBody memoVo memoVo, @CookieValue("refresh") String refresh,HttpServletRequest request,HttpServletResponse response){
        try {
            tokenService.checkRefreshToken(response,refresh);  //refresh check
            tokenService.checkAccessToken(request,response,refresh);
        }catch (RefreshExpiredException e2){
            response.setStatus(392);
            return ;
        }catch (TokenExpiredException e) {
            response.setStatus(390);
            return ;
        }
        //uuid와 같은 메모에 대한 전체를 찾아오기
        List<MemoResponseDto> list = memoService.findMemo(memoVo.getUuid());
        //important변경값 업데이트 저장하기
        memoService.memoUpdate(memoVo.getUuid(),memoVo.getTitle(), memoVo.getContent(), memoVo.isImportant());
     }
    @ApiOperation(value = "클릭한메모에 대한 정보 리턴")
    @GetMapping(value = "/memo/find/userInfo")
    public @ResponseBody ResultMsg<MemoResponseDto> memofindUuid(@RequestParam(value = "uuid") String uuid,@CookieValue("refresh") String refresh,HttpServletRequest request,HttpServletResponse response){
        try {
            tokenService.checkRefreshToken(response,refresh);
            tokenService.checkAccessToken(request,response,refresh);
        }catch (RefreshExpiredException e2){
            response.setStatus(392);
            return new ResultMsg<>(false, "RefreshToken expired", null);
        } catch (TokenExpiredException e) {
            response.setStatus(390);
            return new ResultMsg<>(false, "AccessToken expired", null);
        }
        return new ResultMsg<MemoResponseDto>(true, "memo",memoService.findUserMemo(uuid));
    }
    @ApiOperation(value = "로그인한 사용자 메모 전체 리턴")
    @GetMapping("/memo/find")
    public @ResponseBody ResultMsg<MemoResponseDto> memoFind(@CookieValue("refresh") String refresh,HttpServletRequest request,HttpServletResponse response)//@LoginUser SessionUser user
    {
        String email = null;
        try {
            String token = tokenService.checkAccessToken(request,response,refresh);
            tokenService.checkRefreshToken(response,refresh);
            email = jwtService.getUserNum(token);
            System.out.println(email);
        }catch (RefreshExpiredException e2){
            response.setStatus(392);
            return new ResultMsg<>(false, "RefreshToken expired", null);
        }catch (TokenExpiredException e) {
            response.setStatus(390);
            return new ResultMsg<>(false, "AccessToken expired", null);
        }
        return new ResultMsg<MemoResponseDto>(true, "memo",memoService.findUser(email));
    }
    @ApiOperation(value = "메모 업데이트")
    @PutMapping(value = "/memo/important") //important만 업데이트 해주는 api
    public void memoImportant(@RequestBody uuidVO uuid,@CookieValue("refresh") String refresh,HttpServletRequest request,HttpServletResponse response){
        try {
            tokenService.checkRefreshToken(response,refresh);  //refresh check
            tokenService.checkAccessToken(request,response,refresh);
        }catch (RefreshExpiredException e2){
            response.setStatus(392);
            return ;
        }catch (TokenExpiredException e) {
            response.setStatus(390);
            return ;
        }
        boolean important = memoService.findMemoImportant(uuid.getUuid());
        memoService.updateImportant(uuid.getUuid(), important);
    }
    @ApiOperation(value = "메모를 trash폴더로 옮기기위해 trash field를 true로 수정(복구를 위해)")
    @PutMapping("/memo/trash")
    public void memoTrash(@RequestBody memoVo memoVo, @CookieValue("refresh") String refresh,HttpServletRequest request,HttpServletResponse response){
        System.out.println("trash api 요");
        try {
            tokenService.checkRefreshToken(response,refresh);  //refresh check
            tokenService.checkAccessToken(request,response,refresh);
        }catch (RefreshExpiredException e2){
            response.setStatus(392);
            return ;
        }catch (TokenExpiredException e) {
            response.setStatus(390);
            return ;
        }
        memoService.updateTrash(memoVo.isTrash(),memoVo.getUuid());
    }
    @ApiOperation(value = "메모 최종삭제 다중삭제")
    @DeleteMapping("/memo/delete")
    public void memoDelete(@RequestBody String data, @CookieValue("refresh") String refresh, HttpServletRequest request, HttpServletResponse response) throws ParseException {
        String email = null;
        try {
            tokenService.checkRefreshToken(response,refresh);  //refresh check
            String token = tokenService.checkAccessToken(request,response,refresh);
            email = jwtService.getUserNum(token);
        }catch (RefreshExpiredException e2){
            response.setStatus(392);
            return ;
        }catch (TokenExpiredException e) {
            response.setStatus(390);
            return ;
        }
        JSONParser jsonParser = new JSONParser();
        JSONArray insertParam = (JSONArray) jsonParser.parse(data);
        JSONObject insertData = null;
        if (ObjectUtils.isEmpty(insertParam))
            memoService.deleteAll(email);
        else {
            for (int i = 0; i < insertParam.size(); i++) {
                //배열 안에 있는것도 JSON형식 이기 때문에 JSON Object 로 추출
                insertData = (JSONObject) insertParam.get(i);
                memoService.deleted((String) insertData.get("uuidDelete"));
            }
        }
    }
    @ApiOperation(value = "메모 다중복원")
    @PutMapping("/memo/restore")
    public void memoRestore(@RequestBody String data, @CookieValue("refresh") String refresh, HttpServletRequest request, HttpServletResponse response) throws ParseException {
        try {
            tokenService.checkRefreshToken(response,refresh);  //refresh check
            tokenService.checkAccessToken(request,response,refresh);
        }catch (RefreshExpiredException e2){
            response.setStatus(392);
            return ;
        }catch (TokenExpiredException e) {
            response.setStatus(390);
            return ;
        }
        JSONParser jsonParser = new JSONParser();
        JSONArray insertParam = (JSONArray) jsonParser.parse(data);
        JSONObject insertData = null;
        for(int i=0; i<insertParam.size(); i++) {
            //배열 안에 있는것도 JSON형식 이기 때문에 JSON Object 로 추출
            insertData = (JSONObject) insertParam.get(i);
            memoService.updateTrash(false,(String) insertData.get("uuidDelete"));
        }
    }
}
