package com.project.memo.web.DTO.memoDTO;

import com.project.memo.domain.memoContent.memoContent;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class memoSaveRequestDto {
    private String title;
    private String content;
    private String email;
    private boolean important;
    private int bookMark;
    private String uuid;
    private int user_id; //usre idx 외래키
    private LocalDateTime createdDate;
    @Builder
    public memoSaveRequestDto(String title,String content,String email,boolean important, int bookMark,String uuid,int user_id,LocalDateTime createdDate){
        this.title = title;
        this.content = content;
        this.email = email;
        this.important = important;
        this.bookMark = bookMark;
        this.uuid = uuid;
        this.user_id = user_id;
        this.createdDate = createdDate;
    }

    public memoContent toEntity(){
        return memoContent.builder()
                .title(this.title)
                .content(this.content)
                .email(this.email)
                .important(this.important)
                .bookMark(this.bookMark)
                .uuid(this.uuid)
                .user_id(this.user_id)
                .createdDate(this.createdDate)
                .build();
    }
}
