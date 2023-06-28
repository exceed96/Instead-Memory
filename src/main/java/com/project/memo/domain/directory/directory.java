package com.project.memo.domain.directory;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class directory {
    @Id
    private String uuid;
    private int user_id;
    private String dirName;
    private String email;

    @Builder
    public directory(String dirName,String email,String uuid,int user_id){
        this.dirName = dirName;
        this.email = email;
        this.uuid = uuid;
        this.user_id = user_id;
    }
}
