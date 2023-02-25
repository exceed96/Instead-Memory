package com.project.memo.domain.memoContent;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class memoContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;
    private String title;
    private String content;
    @Builder
    public memoContent(String title, String content){
        this.title = title;
        this.content = content;
    }
}
