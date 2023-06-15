package com.project.memo.web;

import com.project.memo.auth.jwt.JwtService;
import com.project.memo.auth.token.tokenService;
import com.project.memo.common.ResultMsg;
import com.project.memo.service.dirService;
import com.project.memo.service.memoService;
import com.project.memo.service.userService;
import com.project.memo.web.DTO.dirDTO.DirResponseDto;
import com.project.memo.web.DTO.dirDTO.DirSaveRequestDto;
import com.project.memo.web.VO.dirVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    /*
    디렉토리 저장하는 API
    */
    @PostMapping(value = "/dir/save")
    public String dirSave(@RequestBody dirVO dirvo, HttpServletRequest request, HttpServletResponse response){
        /* refreshtoken 확인 */
//        tokenService.checkRefreshToken(response);
        /* access token 유효한지 확인 */
        String token =tokenService.checkAccessToken(request,response);
        String email = jwtService.getUserNum(token);
        //있는 이름인지 확인해야하는 코드 필요
        dirService.sameName(dirvo.getDirName(),email,response);

        String id = UUID.randomUUID().toString();
        DirSaveRequestDto requestDto = new DirSaveRequestDto(dirvo.getDirName(),email,id);
        dirService.save(requestDto);
        return "200";
    }
    /*
    디렉토리 전체 찾아서 리턴해주기
    */
    @GetMapping(value = "/dir/find")
    public @ResponseBody ResultMsg<DirResponseDto> dirFind(HttpServletRequest request, HttpServletResponse response){
        String token = tokenService.checkAccessToken(request,response);
        String email = jwtService.getUserNum(token);
        return new ResultMsg<DirResponseDto>(true, "directory",dirService.getDirectory(email));
    }
    /* 디렉토리 이름 받아오면 memoContent에 dirName업데이트 시켜주기!! */
    @PostMapping(value = "/dir/update") //uuid에 대한 유저 정보를 한줄 찾아오는 ..
    public boolean dirUpdate(@RequestBody dirVO dirvo, HttpServletRequest request, HttpServletResponse response){
        tokenService.checkAccessToken(request,response);
        memoService.updateDirName(dirvo.getUuid(), dirvo.getDirName());
        return true;
    }
    @DeleteMapping("/dir/delete")
    public void memoDelete(@RequestBody dirVO dirvo, HttpServletRequest request, HttpServletResponse response){
        tokenService.checkAccessToken(request,response);
        dirService.deleted(dirvo.getUuid());
    }
}
