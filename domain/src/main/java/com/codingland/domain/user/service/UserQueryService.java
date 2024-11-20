package com.codingland.domain.user.service;

import com.codingland.common.exception.user.UserErrorCode;
import com.codingland.common.exception.user.UserException;
import com.codingland.domain.user.entity.User;
import com.codingland.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserQueryService {

    private final UserRepository userRepository;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserException(UserErrorCode.No_USER_INFO));
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserException(UserErrorCode.No_USER_INFO));
    }
}
