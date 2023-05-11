package com.project.memo.auth.token;

import com.project.memo.web.DTO.memoDTO.memoSaveRequestDto;
import com.project.memo.web.DTO.tokenDTO.TokenSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@RequiredArgsConstructor
@Service
public class tokenService {
    private final TokenRepository tokenRepository;
    @Transactional
    public void save(TokenSaveRequestDto requestDto){
        tokenRepository.save(requestDto.toEntity()).getId(); // 저장
    }

}
