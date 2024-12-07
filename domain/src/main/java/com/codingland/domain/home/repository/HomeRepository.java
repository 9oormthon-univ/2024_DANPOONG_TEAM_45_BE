package com.codingland.domain.home.repository;

import com.codingland.domain.home.entity.Home;
import com.codingland.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HomeRepository extends JpaRepository<Home, Long> {
    Optional<Home> findByUser(User user);
    Optional<Home> findHomeByUserUserId(Long userId);
}