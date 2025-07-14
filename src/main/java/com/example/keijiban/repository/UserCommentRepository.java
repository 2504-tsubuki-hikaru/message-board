package com.example.keijiban.repository;

import com.example.keijiban.dto.UserCommentDto;
import com.example.keijiban.repository.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCommentRepository extends JpaRepository <Comment, Integer>{
    @Query("SELECT new com.example.keijiban.dto.UserCommentDto(" +
            "c.id, c.text, c.user.id, c.message.id, c.createdDate, u.name, u.account) " +
            "FROM Comment c JOIN c.user u " +
            "ORDER BY c.createdDate ASC")
    List<UserCommentDto> getAllUserComments();
}