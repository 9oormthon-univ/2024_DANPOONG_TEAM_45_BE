package com.codingland.domain.user.service;

import com.codingland.common.exception.user.UserErrorCode;
import com.codingland.common.exception.user.UserException;
import com.codingland.domain.user.entity.User;
import com.codingland.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserQueryService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserException(UserErrorCode.No_USER_INFO));
    }

    @Transactional(readOnly = true)
    public User findByUserName(String userName) {
        return userRepository.findByName(userName).orElseThrow(() -> new UserException(UserErrorCode.No_USER_INFO));
    }

    @Transactional(readOnly = true)
    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserException(UserErrorCode.No_USER_INFO));
    }
}
