package com.example.keijiban.service;

import com.example.keijiban.dto.UserFilterDto;
import com.example.keijiban.repository.UserFilterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserFilterService {

    @Autowired
    UserFilterRepository userFilterRepository;

    public UserFilterDto findById(int id) {
        UserFilterDto userFilter = userFilterRepository.findUserDtoById(id);
        return userFilter;
    }
}
