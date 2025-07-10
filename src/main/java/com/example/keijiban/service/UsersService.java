package com.example.keijiban.service;

import com.example.keijiban.repository.UsersRepository;
import com.example.keijiban.repository.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    @Autowired
    UsersRepository usersRepository;

    public Users findByAccountAndPassword(String account, String password) {
        return usersRepository.findByAccountAndPassword(account, password);
    }
}
