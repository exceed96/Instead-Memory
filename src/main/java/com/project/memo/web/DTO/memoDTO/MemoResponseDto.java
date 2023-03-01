package com.project.memo.web.DTO.memoDTO;

import com.project.memo.domain.memoContent.memoContent;

public class MemoResponseDto {
    private int idx;
    private String title;
    private String content;
    private String name;
    private int importante;
    public MemoResponseDto(memoContent entity){
        this.idx = entity.getIdx();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.name = entity.getName();
        this.importante = entity.getImportante();
    }
}
