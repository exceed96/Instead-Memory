package com.project.memo.web;

import com.project.memo.service.memoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import javax.servlet.http.HttpSession;
@RequiredArgsConstructor
@Controller
public class IndexController {
    private final HttpSession httpSession;
    private final memoService memoService;
    @GetMapping("/")
    public String index(Model model){//Model model,@LoginUser SessionUser user
//        SessionUser user = (SessionUser) httpSession.getAttribute("user");
//        if(user != null) {
//            model.addAttribute("userName", user.getName());
//
//            model.addAttribute("memo", memoService.findUser(user.getEmail()));
//        }
        return "index";
    }

    @GetMapping("/memo/save")
    public String memoSave() {
        return "memo-save";
    } //이런부분은 html리턴 나중에 지울부분
}
