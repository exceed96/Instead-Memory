package com.project.memo.web;

import com.project.memo.auth.LoginUser;
import com.project.memo.auth.dto.SessionUser;
import com.project.memo.common.ResultMsg;
import com.project.memo.service.memoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins="*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class userApiController {
    private final memoService memoService;
}
