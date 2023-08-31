package com.project.memo.web.VO;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class memoVo {
    String title;
    String content;
    boolean important;
    String uuid;
    boolean trash;
}
