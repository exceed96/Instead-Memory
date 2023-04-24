package com.project.memo.web.DTO.memoDTO;

import com.project.memo.domain.memoContent.memoContent;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class memoSaveRequestDto {
    private String title;
    private String content;
    private String email;
    private int important;
    private int bookMark;
    @Builder
    public memoSaveRequestDto(String title,String content,String email,int important, int bookMark){
        this.title = title;
        this.content = content;
        this.email = email;
        this.important = important;
        this.bookMark = bookMark;
    }

    public memoContent toEntity(){
        return memoContent.builder()
                .title(this.title)
                .content(this.content)
                .email(this.email)
                .important(this.important)
                .bookMark(this.bookMark)
                .build();
    }
}
