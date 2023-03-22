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
    private String email;
    private int importante;
    private int bookMark;
    @Builder
    public memoContent(String title, String content,String email, int importante, int bookMark){
        this.title = title;
        this.content = content;
        this.importante = importante;
        this.email = email;
        this.bookMark = bookMark;
    }
}
