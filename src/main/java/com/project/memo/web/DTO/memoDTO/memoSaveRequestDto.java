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
    private String name;
    private int importante;
    @Builder
    public memoSaveRequestDto(String title,String content,String name,int importante){
        this.title = title;
        this.content = content;
        this.name = name;
        this.importante = importante;
    }

    public memoContent toEntity(){
        return memoContent.builder()
                .title(this.title)
                .content(this.content)
                .name(this.name)
                .importante(this.importante)
                .build();
    }
}
