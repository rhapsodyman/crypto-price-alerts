package com.rhapsodyman.cryptoalerts.repository;

import com.rhapsodyman.cryptoalerts.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByPhone(String phone);
}