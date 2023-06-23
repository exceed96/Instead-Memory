package com.project.memo.web.DTO.memoDTO;

import com.project.memo.domain.memoContent.memoContent;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemoResponseWithCntDto {
    private String title;
    private String content;
    private String email;
    private boolean important;
    private int bookMark;
    private String uuid;
    public MemoResponseWithCntDto(String title,String content,String email,boolean important, int bookMark,String uuid){
        this.uuid = uuid;
        this.title = title;
        this.content = content;
        this.email = email;
        this.important = important;
        this.bookMark = bookMark;
    }
}
