package com.project.memo.web;

import com.project.memo.auth.LoginUser;
import com.project.memo.auth.dto.SessionUser;
import com.project.memo.common.ResultMsg;
import com.project.memo.service.memoService;
import com.project.memo.web.DTO.memoDTO.MemoResponseDto;
import com.project.memo.web.DTO.memoDTO.memoSaveRequestDto;
import com.project.memo.web.VO.memoVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins="*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class memoApiController {
    private final memoService memoService;
    @CrossOrigin(origins="*")
    @PostMapping("/v1/memo")
    public void memoSave(@RequestBody memoVo memoVo, @LoginUser SessionUser user){//
        memoSaveRequestDto requestDto;
        String title = memoVo.getTitle();
        String content = memoVo.getContent();
        int importante = memoVo.getImportante();
        int bookMark = memoVo.getBookMark();
        requestDto = new memoSaveRequestDto(title,content,user.getEmail(), importante,bookMark);
        memoService.save(requestDto);
    }
    @CrossOrigin(origins="*")
    @GetMapping("/v1/memo/find")
    public @ResponseBody ResultMsg<MemoResponseDto> memoFind()//@LoginUser SessionUser user
    {
        return new ResultMsg<MemoResponseDto>(true, "memo",memoService.findUser("(디스플레이공학전공)김상엽"));
    }

}
