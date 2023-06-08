package com.project.memo.web;


import com.project.memo.auth.jwt.JwtService;
import com.project.memo.auth.token.makeCookie;
import com.project.memo.auth.token.tokenService;
import com.project.memo.common.ResultMsg;
import com.project.memo.service.memoService;
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

    private final makeCookie makeCookie;
    private final tokenService tokenService;
    @Value("${jwt.token.secret.key}")
    private String JWT_SECRET_KEY;

//    @PostMapping("/v1/memo")
    @RequestMapping(value = "/memo") //처음 저장하는 api
    public boolean memoSave(@RequestBody memoVo memoVo,HttpServletRequest request){//, @LoginUser SessionUser user
        memoSaveRequestDto requestDto;
        String title = memoVo.getTitle();
        String content = memoVo.getContent();

        String jwtToken = request.getHeader("Authorization");

//         JWT 토큰 사용하기
        String email = jwtService.getUserNum(jwtToken);
        System.out.println("여기는 메모저장 이메일 입니다. " +email);
        String id = UUID.randomUUID().toString(); // idx(int)로 구분하지 않고 랜덤 해시값을 통해 메모 구분
        boolean important = memoVo.isImportant();
//        int bookMark = memoVo.getBookMark();
        requestDto = new memoSaveRequestDto(title,content,email, important,0,id);
        memoService.save(requestDto);
        return true;
    }
    @PutMapping(value = "/memo/update") //memo 전체 업데이트
    public boolean memoImportant(@RequestBody memoVo memoVo, HttpServletRequest request){
        String jwtToken = request.getHeader("Authorization");
        boolean important = memoVo.isImportant();
        String title = memoVo.getTitle();
        String content = memoVo.getContent();
        String uuid = memoVo.getUuid();

        //uuid와 같은 메모에 대한 전체를 찾아오기
        List<MemoResponseDto> list = memoService.findMemo(uuid);
        //important변경값 업데이트 저장하기
        memoService.memoUpdate(uuid,title, content, important);
        return true;
     }
    @GetMapping(value = "/memo/find/userInfo") //uuid에 대한 유저 정보를 한줄 찾아오는 ..
    public @ResponseBody ResultMsg<MemoResponseDto> memofindUuid(@RequestParam(value = "uuid") String uuid,HttpServletRequest request){
        return new ResultMsg<MemoResponseDto>(true, "memo",memoService.findUserMemo(uuid));
    }
    @GetMapping("/memo/find") //찾아서 그 친구와 맞는 사람의 메모 return
    public @ResponseBody ResultMsg<MemoResponseDto> memoFind(HttpServletRequest request, HttpServletResponse response)//@LoginUser SessionUser user
    {
        //,@CookieValue(value="ID",required = false) String key
//        String accessToken = tokenService.findAccessToken(res, key);
        String jwtToken = request.getHeader("Authorization");
        boolean check = jwtService.validateToken(jwtToken.replace("Bearer",""));
        if (!check)
            return new ResultMsg<MemoResponseDto>(false,"access Token validate expiration");
        // JWT 토큰 사용하기
        String email = jwtService.getUserNum(jwtToken);
        System.out.println("여기는 메모find 이메일 입니다. " +email);
        //!! email값에 아직 아무 메모내용이없을때 에러뜨는거 수정해야함
        return new ResultMsg<MemoResponseDto>(true, "memo",memoService.findUser(email));
    }
    @PutMapping(value = "/memo/important") //important만 업데이트 해주는 api
    public String memoImportant(@RequestBody uuidVO uuid, HttpServletRequest request){
        String id = uuid.getUuid();
        boolean important = memoService.findMemoImportant(id);
        System.out.println("v1/memo/important : " + important);
        if (important == false) important = true;
        else important = false;
        System.out.println("v1/memo/important : change " + important);
        memoService.updateImportant(id, important);
        return "200";
    }
    @DeleteMapping("/memo/delete/{uuid}")
    public void memoDelete(@PathVariable("uuid") String uuid){
        System.out.println(uuid);
        memoService.deleted(uuid);
    }
}
