package com.project.memo.web;


import com.project.memo.common.ResultMsg;
import com.project.memo.service.memoService;
import com.project.memo.web.DTO.memoDTO.MemoResponseDto;
import com.project.memo.web.DTO.memoDTO.memoSaveRequestDto;
import com.project.memo.web.VO.memoVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpHeaders;
import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://43.200.92.244:8000")
public class memoApiController {
    private final memoService memoService;

//    @PostMapping("/v1/memo")
    @RequestMapping(value = "/v1/memo")
    public boolean memoSave(@RequestBody memoVo memoVo,HttpServletRequest request){//, @LoginUser SessionUser user
        memoSaveRequestDto requestDto;
        String title = memoVo.getTitle();
        String content = memoVo.getContent();
        String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");
        // JWT 토큰 사용하기
        System.out.println(title+ " " +content);
        System.out.println(jwtToken);
//        int importante = memoVo.getImportante();
//        int bookMark = memoVo.getBookMark();
//        requestDto = new memoSaveRequestDto(title,content,user.getEmail(), importante,bookMark);
//        memoService.save(requestDto);
        return true;
    }
    @GetMapping("/v1/memo/find")
    public @ResponseBody ResultMsg<MemoResponseDto> memoFind(HttpServletRequest request)//@LoginUser SessionUser user
    {
        ResultMsg<MemoResponseDto> aa = (ResultMsg<MemoResponseDto>) memoService.findUser("exceed_96@naver.com");
        System.out.println(aa.getData());
        return new ResultMsg<MemoResponseDto>(true, "memo",memoService.findUser("exceed_96@naver.com"));
    }
}
