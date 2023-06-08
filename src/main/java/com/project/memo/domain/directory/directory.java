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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;
    private String dirName;
    @Builder
    public directory(String dirName){
        this.dirName = dirName;
    }
}
