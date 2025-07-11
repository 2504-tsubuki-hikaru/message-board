package com.example.keijiban.repository;

import com.example.keijiban.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<User, Integer> {
    User findByAccountAndPassword(String account, String password);
}
