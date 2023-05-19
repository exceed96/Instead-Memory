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
    private boolean important;
    private int bookMark;
    private String uuid;
    @Builder
    public memoSaveRequestDto(String title,String content,String email,boolean important, int bookMark,String uuid){
        this.title = title;
        this.content = content;
        this.email = email;
        this.important = important;
        this.bookMark = bookMark;
        this.uuid = uuid;
    }

    public memoContent toEntity(){
        return memoContent.builder()
                .title(this.title)
                .content(this.content)
                .email(this.email)
                .important(this.important)
                .bookMark(this.bookMark)
                .uuid(this.uuid)
                .build();
    }
}
