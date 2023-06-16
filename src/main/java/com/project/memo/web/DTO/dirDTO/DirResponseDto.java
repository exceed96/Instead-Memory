package com.project.memo.web.DTO.dirDTO;

import com.project.memo.domain.directory.directory;
import com.project.memo.domain.memoContent.memoContent;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DirResponseDto {
    private String dirName;
    private String email;
    private String uuid;
    public DirResponseDto(directory entity){
        this.dirName = entity.getDirName();
        this.email = entity.getEmail();
        this.uuid = entity.getUuid();
    }
}
