package com.example.keijiban.repository;

import com.example.keijiban.dto.UserMessageDto;
import com.example.keijiban.repository.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMessageRepository extends JpaRepository<Message, String> {
    @Query("SELECT new com.example.keijiban.dto.UserMessageDto(m.id, m.text, m.category, u.id, u.name, u.account) " +
            "FROM Message m JOIN m.user u")
    List<UserMessageDto> findMessageUserDetails();
}