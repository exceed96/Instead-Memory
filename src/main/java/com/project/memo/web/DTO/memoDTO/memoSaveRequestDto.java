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

    @Builder
    public memoSaveRequestDto(String title,String content){
        this.title = title;
        this.content = content;
    }

    public memoContent toEntity(){
        return memoContent.builder()
                .title(this.title)
                .content(this.content)
                .build();
    }
}
