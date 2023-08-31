package com.project.memo.web;

import com.project.memo.auth.jwt.JwtService;
import com.project.memo.auth.token.tokenService;
import com.project.memo.common.ResultMsg;
import com.project.memo.exception.DirSameNameException;
import com.project.memo.exception.RefreshExpiredException;
import com.project.memo.exception.TokenExpiredException;
import com.project.memo.service.dirService;
import com.project.memo.service.memoService;
import com.project.memo.service.userService;
import com.project.memo.web.DTO.dirDTO.DirResponseDto;
import com.project.memo.web.DTO.dirDTO.DirSaveRequestDto;
import com.project.memo.web.VO.dirVO;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.ApplicationScope;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.UUID;

import static com.project.memo.util.Define.DIR_VERSION_PATH;


@RequiredArgsConstructor
@RestController
@RequestMapping(value = DIR_VERSION_PATH)
@CrossOrigin(origins = "https://insteadmemo.kr")
public class directoryContorller {
    private final dirService dirService;
    private final memoService memoService;
    private final JwtService jwtService;
    private final tokenService tokenService;
    private final userService userService;

    @ApiOperation(value = "디렉토리 첫 생성시")
    @PostMapping(value = "/dir/save")
    public void dirSave(@RequestBody dirVO dirvo,@CookieValue("refresh") String refresh, HttpServletRequest request, HttpServletResponse response){
        String token = null;
        String email = null;
        try {
            tokenService.checkRefreshToken(response,refresh);  //refresh check
            token = tokenService.checkAccessToken(request,response,refresh);
            email = jwtService.getUserNum(token);
            dirService.sameName(dirvo.getDirName(), email, response);
        } catch (DirSameNameException e) {
            response.setStatus(391);
            return ;
        }catch (TokenExpiredException e) {
            response.setStatus(390);
            return ;
        }catch (RefreshExpiredException e){
            response.setStatus(392);
            return ;
        }
        String id = UUID.randomUUID().toString();
        int user_id = userService.user_key(email); //외래키설정에 넣어주기위한 user테이블에서 idx키를 찾아와서 memoContent에 저장
        DirSaveRequestDto requestDto = new DirSaveRequestDto(dirvo.getDirName(),email,id,user_id);
        dirService.save(requestDto);
    }
    @ApiOperation(value = "디렉토리 테이블 전체 반환")
    @GetMapping(value = "/dir/find")
    public @ResponseBody ResultMsg<DirResponseDto> dirFind(@CookieValue("refresh") String refresh,HttpServletRequest request, HttpServletResponse response){
        String token = null;
        try {
            tokenService.checkRefreshToken(response,refresh);  //refresh check
            token = tokenService.checkAccessToken(request,response,refresh);
        }catch (RefreshExpiredException e){
            response.setStatus(392);
            return new ResultMsg<>(false, "RefreshToken expired", null);
        }catch (TokenExpiredException e) {
            response.setStatus(390);
            return new ResultMsg<>(false, "AccessToken expired", null);
        }
        String email = jwtService.getUserNum(token);
        return new ResultMsg<DirResponseDto>(true, "directory",dirService.getDirectory(email));
    }
    @ApiOperation(value = "디렉토리 이름 수정")
    @PutMapping(value = "/dir/update")
    public void dirUpdate(@RequestBody dirVO dirvo,@CookieValue("refresh") String refresh, HttpServletRequest request, HttpServletResponse response){
        try {
            tokenService.checkRefreshToken(response,refresh);  //refresh check
            String token = tokenService.checkAccessToken(request,response,refresh);
            String email = jwtService.getUserNum(token);
            dirService.sameName(dirvo.getDirName(), email, response);
        }catch (TokenExpiredException e) {
            response.setStatus(390);
            return ;
        }catch (DirSameNameException e) {
            response.setStatus(391);
            return ;
        }catch (RefreshExpiredException e){
            response.setStatus(392);
            return ;
        }
        dirService.updateDirName(dirvo.getUuid(), dirvo.getDirName());
    }
    @ApiOperation(value = "디렉토리 uuid를 memo table directory_id외래키에 저장")
    @PutMapping(value = "/dir/memo/dirfk")
    public void dirUuid(@RequestBody dirVO dirvo,@CookieValue("refresh") String refresh, HttpServletRequest request, HttpServletResponse response){
        try {
            tokenService.checkRefreshToken(response,refresh);  //refresh check
            String token = tokenService.checkAccessToken(request,response,refresh);
            String email = jwtService.getUserNum(token);
        }catch (TokenExpiredException e) {
            response.setStatus(390);
            return ;
        }catch (RefreshExpiredException e){
            response.setStatus(392);
            return ;
        }
        System.out.println("diruuid : " + dirvo.getUuid() + ", memouuid : " + dirvo.getMemoUuid());
        memoService.updateDrectory_id(dirvo.getUuid(),dirvo.getMemoUuid());
    }
    @ApiOperation(value = "디렉토리 삭제")
    @DeleteMapping("/dir/delete")
    public void dirDelete(@RequestBody dirVO dirvo,@CookieValue("refresh") String refresh, HttpServletRequest request, HttpServletResponse response){
        try {
            tokenService.checkRefreshToken(response,refresh);  //refresh check
            tokenService.checkAccessToken(request,response,refresh);
        } catch (TokenExpiredException e) {
            response.setStatus(390);
            return ;
        }catch (RefreshExpiredException e){
            response.setStatus(392);
            return ;
        }
        //getUuid가 저장되어있는 메모테이블의 directory_id를 모두 null
        memoService.saveNull(dirvo.getUuid());
        dirService.deleted(dirvo.getUuid());
    }
}
