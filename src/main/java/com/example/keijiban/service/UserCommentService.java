package com.example.keijiban.service;

import com.example.keijiban.dto.UserCommentDto;
import com.example.keijiban.repository.UserCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCommentService {

    @Autowired
    UserCommentRepository userCommentRepository;

    public List<UserCommentDto> getAllUserComments() {
        List<UserCommentDto> userComments = userCommentRepository.getAllUserComments();
        return userComments;
    }
}
