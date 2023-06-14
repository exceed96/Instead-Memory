package com.project.memo.web;

import com.project.memo.auth.jwt.JwtService;
import com.project.memo.auth.token.tokenService;
import com.project.memo.common.ResultMsg;
import com.project.memo.service.dirService;
import com.project.memo.service.memoService;
import com.project.memo.service.userService;
import com.project.memo.web.DTO.dirDTO.DirResponseDto;
import com.project.memo.web.DTO.dirDTO.DirSaveRequestDto;
import com.project.memo.web.DTO.memoDTO.MemoResponseDto;
import com.project.memo.web.VO.dirVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        //있는 이름인지 확인해야하는 코드 필요
        int num = dirService.sameName(dirvo.getDirName());
        if (num != 0) {
            response.setStatus(606);
            return "중복된이름";
        }
        String email = jwtService.getUserNum(token);
        DirSaveRequestDto requestDto = new DirSaveRequestDto(dirvo.getDirName(),email);
        dirService.save(requestDto);
        return "200";
    }
    /*
    디렉토리 전체 찾아서 리턴해주기
    */
    @GetMapping(value = "/dir/find")
    public @ResponseBody ResultMsg<DirResponseDto> dirFind(HttpServletRequest request){
        String jwtToken = request.getHeader("Authorization");
        String email = jwtService.getUserNum(jwtToken);
        return new ResultMsg<DirResponseDto>(true, "directory",dirService.getDirectory(email));
    }
    /* 디렉토리 이름 받아오면 memoContent에 dirName업데이트 시켜주기!! */
    @PostMapping(value = "/dir/update") //uuid에 대한 유저 정보를 한줄 찾아오는 ..
    public boolean dirUpdate(@RequestBody dirVO dirvo, HttpServletRequest request){
        memoService.updateDirName(dirvo.getUuid(), dirvo.getDirName());
        return true;
    }
    @DeleteMapping("/dir/delete")
    public void memoDelete(@RequestBody dirVO dirvo, HttpServletRequest request){
        dirService.deleted(dirvo.getDirName());
    }
}
