package com.codingland.domain.user.repository;

import com.codingland.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findBySubId(String subId);

    Optional<User> findByEmail(String email);

    Optional<User> findByName(String name);

    void deleteByName(String name);
}
