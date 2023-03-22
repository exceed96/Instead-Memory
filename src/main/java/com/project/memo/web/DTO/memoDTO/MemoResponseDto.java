package com.project.memo.web.DTO.memoDTO;

import com.project.memo.domain.memoContent.memoContent;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemoResponseDto {
    private int idx;
    private String title;
    private String content;
    private String email;
    private int importante;
    private int bookMark;
    public MemoResponseDto(memoContent entity){
        this.idx = entity.getIdx();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.email = entity.getEmail();
        this.importante = entity.getImportante();
        this.bookMark = entity.getBookMark();
    }
}
