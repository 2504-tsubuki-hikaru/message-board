package com.example.keijiban.service;

import com.example.keijiban.dto.UserBranchDepartDto;
import com.example.keijiban.repository.UserBranchDepartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBranchDepartService {

    @Autowired
    UserBranchDepartRepository userBranchDepartRepository;

    public List<UserBranchDepartDto> findAllUserBranchDepart () {
        List<UserBranchDepartDto> ubdDto = userBranchDepartRepository.findAllUserBranchDepart();
        return ubdDto;
    }
}
