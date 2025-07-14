package com.example.keijiban.repository;

import com.example.keijiban.dto.UserMessageDto;
import com.example.keijiban.repository.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserMessageRepository extends JpaRepository<Message, Integer> {
    //category引数なし
    @Query("SELECT new com.example.keijiban.dto.UserMessageDto(" +
            "m.id, m.text, m.user.id, m.category, m.createdDate, m.title, u.name, u.account) " +
            "FROM Message m JOIN m.user u " +
            "WHERE m.createdDate BETWEEN :startDate AND :endDate " +
            "ORDER BY m.createdDate DESC")
    List<UserMessageDto> findByCreatedDateBetween(@Param("startDate") Date startDate,
                                                  @Param("endDate") Date endDate);

    //category引数あり
    @Query("SELECT new com.example.keijiban.dto.UserMessageDto(" +
            "m.id, m.text, u.id, m.category, m.createdDate, m.title, u.name, u.account) " +
            "FROM Message m JOIN m.user u " +
            "WHERE m.createdDate BETWEEN :startDate AND :endDate AND m.category LIKE %:category% " +
            "ORDER BY m.createdDate DESC")
    List<UserMessageDto> findByCreatedDateBetweenAndCategory(@Param("startDate") Date startDate,
                                                             @Param("endDate") Date endDate,
                                                             @Param("category") String category);

}