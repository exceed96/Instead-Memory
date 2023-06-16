package com.project.memo.service;

import com.project.memo.domain.directory.directoryRepository;
import com.project.memo.exception.DirSameNameException;
import com.project.memo.web.DTO.dirDTO.DirResponseDto;
import com.project.memo.web.DTO.dirDTO.DirSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
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
    public void sameName(String dirName,String email, HttpServletResponse response){
       int num = directoryRepository.sameName(dirName,email);
        if (num != 0)
            throw new DirSameNameException();
    }

    @Transactional
    public void deleted(String uuid) {
        directoryRepository.dirDelete(uuid);
    }
    @Transactional
    public void updateDirName(String uuid,String dirName) {
        directoryRepository.updateDirName(uuid,dirName);
    }
}
