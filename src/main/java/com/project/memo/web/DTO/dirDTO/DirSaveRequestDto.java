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
    private String email;
    private String uuid;
    private int user_id;
    @Builder
    public DirSaveRequestDto(String dirName,String email,String uuid,int user_id){
        this.dirName = dirName;
        this.email = email;
        this.uuid = uuid;
        this.user_id = user_id;
    }

    public directory toEntity(){
        return directory.builder()
                .dirName(this.dirName)
                .email(this.email)
                .uuid(this.uuid)
                .user_id(this.user_id)
                .build();
    }
}
