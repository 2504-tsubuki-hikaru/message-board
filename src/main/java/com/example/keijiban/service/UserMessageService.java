package com.example.keijiban.service;

import com.example.keijiban.dto.UserMessageDto;
import com.example.keijiban.repository.UserMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMessageService {

    @Autowired
    UserMessageRepository userMessageRepository;

    public List<UserMessageDto> findByUserMessages() {
        return userMessageRepository.findMessageUserDetails();
    }
}
