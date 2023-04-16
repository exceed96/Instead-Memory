package com.project.memo.web;


import com.project.memo.common.ResultMsg;
import com.project.memo.service.memoService;
import com.project.memo.web.DTO.memoDTO.MemoResponseDto;
import com.project.memo.web.DTO.memoDTO.memoSaveRequestDto;
import com.project.memo.web.VO.memoVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins="http://localhost:8080", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class memoApiController {
    private final memoService memoService;

    @PostMapping("/v1/memo")
    public void memoSave(@RequestBody memoVo memoVo){//, @LoginUser SessionUser user
        memoSaveRequestDto requestDto;
        String title = memoVo.getTitle();
        String content = memoVo.getContent();
        int importante = memoVo.getImportante();
        int bookMark = memoVo.getBookMark();
//        requestDto = new memoSaveRequestDto(title,content,user.getEmail(), importante,bookMark);
//        memoService.save(requestDto);
    }
//    @GetMapping("/v1/memo/find")
//    public @ResponseBody ResultMsg<MemoResponseDto> memoFind()//@LoginUser SessionUser user
//    {
//        return new ResultMsg<MemoResponseDto>(true, "memo",memoService.findUser(user.getEmail()));
//    }
}
