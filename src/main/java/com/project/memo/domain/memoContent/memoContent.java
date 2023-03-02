package com.project.memo.domain.memoContent;

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
public class memoContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;
    private String title;
    private String content;
    private String name;
    private int importante;
    @Builder
    public memoContent(String title, String content,String name, int importante){
        this.title = title;
        this.content = content;
        this.importante = importante;
        this.name = name;
    }
}
