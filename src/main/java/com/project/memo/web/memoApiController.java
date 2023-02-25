package com.project.memo.web;

import com.project.memo.service.memoService;
import com.project.memo.web.DTO.memoDTO.memoSaveRequestDto;
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
    public void memoSave(@RequestBody memoSaveRequestDto requestDto){
        memoService.save(requestDto);
    }
}
