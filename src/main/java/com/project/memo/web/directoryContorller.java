package com.project.memo.web;

import com.project.memo.common.ResultMsg;
import com.project.memo.service.dirService;
import com.project.memo.service.memoService;
import com.project.memo.web.DTO.dirDTO.DirSaveRequestDto;
import com.project.memo.web.DTO.memoDTO.MemoResponseDto;
import com.project.memo.web.VO.dirVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.project.memo.util.Define.DIR_VERSION_PATH;


@RequiredArgsConstructor
@RestController
@RequestMapping(value = DIR_VERSION_PATH)
@CrossOrigin(origins = "https://insteadmemo.kr")
public class directoryContorller {
    private final dirService dirService;
    private final memoService memoService;
    @PostMapping(value = "/dir/save") //uuid에 대한 유저 정보를 한줄 찾아오는 ..
    public boolean dirSave(@RequestParam(value = "dirName") String dirName, HttpServletRequest request){
        //있는 이름인지 확인해야하는 코드 필요
        DirSaveRequestDto requestDto = new DirSaveRequestDto(dirName);
        dirService.save(requestDto);
        return true;
    }
    /* 디렉토리 이름 받아오면 memoContent에 dirName업데이트 시켜주기!! */
    @PostMapping(value = "/dir/update") //uuid에 대한 유저 정보를 한줄 찾아오는 ..
    public boolean dirUpdate(@RequestBody dirVO dirvo, HttpServletRequest request){
        memoService.updateDirName(dirvo.getUuid(), dirvo.getDirName());
        return true;
    }
}
