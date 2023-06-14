package com.project.memo.service;

import com.project.memo.domain.directory.directoryRepository;
import com.project.memo.web.DTO.dirDTO.DirResponseDto;
import com.project.memo.web.DTO.dirDTO.DirSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class dirService {
    private final directoryRepository directoryRepository;
    @Transactional
    public void save(DirSaveRequestDto requestDto){
        directoryRepository.save(requestDto.toEntity()).getIdx(); // 저장
    }
    @Transactional
    public List<DirResponseDto> getDirectory(String email){
        return directoryRepository.getDir(email).stream()
                .map(DirResponseDto::new)
                .collect(Collectors.toList());
    }
    @Transactional
    public int sameName(String dirName){
        return directoryRepository.sameName(dirName);
    }

    @Transactional
    public void deleted(String dirName) {
        directoryRepository.dirDelete(dirName);
    }
}
