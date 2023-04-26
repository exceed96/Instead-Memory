package com.project.memo.web.DTO.memoDTO;

import com.project.memo.domain.memoContent.memoContent;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemoResponseDto {
    private String title;
    private String content;
    private String email;
    private int important;
    private int bookMark;
    private String uuid;
    public MemoResponseDto(memoContent entity){
        this.uuid = entity.getUuid();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.email = entity.getEmail();
        this.important = entity.getImportant();
        this.bookMark = entity.getBookMark();
    }
}
