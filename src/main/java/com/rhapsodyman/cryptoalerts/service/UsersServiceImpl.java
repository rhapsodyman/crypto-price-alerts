package com.rhapsodyman.cryptoalerts.service;


import com.rhapsodyman.cryptoalerts.domain.User;
import com.rhapsodyman.cryptoalerts.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("usersService")
public class UsersServiceImpl implements IUsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public long registerUser(User user) {
        User saved = usersRepository.saveAndFlush(user);
        return saved.getId();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findById(long id) {
        return usersRepository.findById(id);
    }
}