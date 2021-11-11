package com.rhapsodyman.cryptoalerts.service;

import com.rhapsodyman.cryptoalerts.domain.User;

import java.util.Optional;

public interface IUsersService {

    long registerUser(User user);

    Optional<User> findByUsername(String username);

    Optional<User> findById(long id);


}