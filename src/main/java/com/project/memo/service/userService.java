package com.project.memo.service;

import com.project.memo.domain.user.UserRepository;
import com.project.memo.web.DTO.userDTO.UserSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@RequiredArgsConstructor
@Service
public class userService {
    private final UserRepository userRepository;

    @Transactional
    public void save(UserSaveRequestDto requestDto){
        userRepository.save(requestDto.toEntity()).getIdx(); // 저장
    }
    @Transactional
    public String findUserEmail(String email){
        return userRepository.findAllIndex(email);
    }
    @Transactional
    public String findUserName(String email){
        return userRepository.findUser(email);
    }

}
