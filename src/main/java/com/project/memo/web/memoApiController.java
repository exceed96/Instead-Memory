package com.project.memo.web;

import com.project.memo.auth.LoginUser;
import com.project.memo.auth.dto.SessionUser;
import com.project.memo.service.memoService;
import com.project.memo.web.DTO.memoDTO.memoSaveRequestDto;
import com.project.memo.web.VO.memoVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin(origins="*")
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
        requestDto = new memoSaveRequestDto(title,content,user.getName(), importante);
        memoService.save(requestDto);
    }


}
