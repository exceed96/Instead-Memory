package com.project.memo.web.DTO.dirDTO;

import com.project.memo.domain.directory.directory;
import com.project.memo.domain.memoContent.memoContent;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DirSaveRequestDto {
    private String dirName;
    @Builder
    public DirSaveRequestDto(String dirName){
        this.dirName = dirName;
    }

    public directory toEntity(){
        return directory.builder()
                .dirName(this.dirName)
                .build();
    }
}
