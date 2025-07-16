package com.example.keijiban.repository;

import com.example.keijiban.repository.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<User, Integer> {
    User findByAccountAndPassword(String account, String password);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.isStopped = :isStopped WHERE u.id = :id")
    void updateIsStopped(@Param("id")Integer id, @Param("isStopped") Integer isStopped);
}