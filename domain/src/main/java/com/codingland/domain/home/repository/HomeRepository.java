package com.codingland.domain.home.repository;

import com.codingland.domain.home.entity.Home;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeRepository extends JpaRepository<Home, Long> {
}