package com.project.memo.service;

import com.project.memo.domain.directory.directoryRepository;
import com.project.memo.domain.memoContent.memoContentRepository;
import com.project.memo.web.DTO.dirDTO.DirSaveRequestDto;
import com.project.memo.web.DTO.memoDTO.memoSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class dirService {
    private final directoryRepository directoryRepository;
    @Transactional
    public void save(DirSaveRequestDto requestDto){
        directoryRepository.save(requestDto.toEntity()).getIdx(); // 저장
    }
}
