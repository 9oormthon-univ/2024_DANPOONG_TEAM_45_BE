package com.codingland.domain.home.repository;

import com.codingland.domain.home.entity.Home;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HomeRepository extends JpaRepository<Home, Long> {
    Optional<Home> findHomeByUserUserId(Long userId);
}