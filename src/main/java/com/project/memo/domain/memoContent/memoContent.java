package com.project.memo.domain.memoContent;

import com.project.memo.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.assertj.core.api.AbstractIterableAssert;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class memoContent {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String uuid;
    private int user_id;
    private String directory_id;
    private String title;
    private String content;
    private String email;
    private boolean important;
    private int bookMark;
    private boolean trash;
    @CreatedDate // Entity가 생성되어 저장될 때 시간 자동 저장
    @Column(updatable = false)
    private LocalDateTime createdDate;
    @Builder
    public memoContent(String title, String content,String email, boolean important, int bookMark,String uuid,int user_id,boolean trash,String directory_id,LocalDateTime createdDate){

        this.title = title;
        this.content = content;
        this.important = important;
        this.email = email;
        this.bookMark = bookMark;
        this.uuid = uuid;
        this.user_id = user_id;
        this.trash = trash;
        this.directory_id = directory_id;
        this.createdDate = createdDate;
    }

}
