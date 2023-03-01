package com.project.memo.service;

import com.project.memo.domain.memoContent.memoContent;
import com.project.memo.domain.memoContent.memoContentRepository;
import com.project.memo.web.DTO.memoDTO.MemoResponseDto;
import com.project.memo.web.DTO.memoDTO.memoSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class memoService {
    private final memoContentRepository memoContentRepository;

    @Transactional
    public void save(memoSaveRequestDto requestDto){
        memoContentRepository.save(requestDto.toEntity()).getIdx(); // 저장
    }

    public MemoResponseDto findUser(String user) {
        memoContent memo = memoContentRepository.findByUser(user);
        MemoResponseDto responseDto = new MemoResponseDto(memo);

        return responseDto;
    }
}
