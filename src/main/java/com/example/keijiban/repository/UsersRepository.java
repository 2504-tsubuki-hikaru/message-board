package com.example.keijiban.repository;

import com.example.keijiban.repository.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
    Users findByAccountAndPassword(String account, String password);
}
