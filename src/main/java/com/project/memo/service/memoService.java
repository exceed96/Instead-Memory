package com.project.memo.service;

import com.project.memo.domain.memoContent.memoContent;
import com.project.memo.domain.memoContent.memoContentRepository;
import com.project.memo.web.DTO.memoDTO.MemoResponseDto;
import com.project.memo.web.DTO.memoDTO.memoSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class memoService {
    private final memoContentRepository memoContentRepository;

    @Transactional
    public void save(memoSaveRequestDto requestDto){
        memoContentRepository.save(requestDto.toEntity()).getIdx(); // 저장
    }

    @Transactional
    public List<MemoResponseDto> findUser(String email){
        return memoContentRepository.findByUser(email).stream()
                .map(MemoResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<MemoResponseDto> findMemo(String uuid){
        return memoContentRepository.findMemoLine(uuid).stream()
                .map(MemoResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleted(String uuid) {
        memoContentRepository.menuDelete(uuid);
    }
    @Transactional
    public void memoUpdate(String uuid,String title,String content, int important) {
        memoContentRepository.memoUpdated(uuid,important,title,content);
    }

}
