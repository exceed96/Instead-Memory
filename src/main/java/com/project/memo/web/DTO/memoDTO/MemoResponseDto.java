package com.project.memo.web.DTO.memoDTO;

import com.project.memo.domain.memoContent.memoContent;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@NoArgsConstructor
public class MemoResponseDto {
    private String title;
    private String content;
    private String email;
    private boolean important;
    private int bookMark;
    private String uuid;
    private String directory_id;
    private boolean trash;
    private LocalDateTime createdDate;
    public MemoResponseDto(memoContent entity){
        this.uuid = entity.getUuid();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.email = entity.getEmail();
        this.important = entity.isImportant();
        this.bookMark = entity.getBookMark();
        this.trash = entity.isTrash();
        this.directory_id = entity.getDirectory_id();
        this.createdDate = entity.getCreatedDate();
    }
}
