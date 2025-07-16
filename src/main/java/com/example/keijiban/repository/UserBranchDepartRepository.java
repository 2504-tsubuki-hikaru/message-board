package com.example.keijiban.repository;

import com.example.keijiban.dto.UserBranchDepartDto;
import com.example.keijiban.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserBranchDepartRepository extends JpaRepository<User, Integer> {
    @Query("""
    SELECT new com.example.keijiban.dto.UserBranchDepartDto(
    u.id, u.account, u.name,u.branch.id, u.department.id, u.isStopped,b.name, d.name)
    FROM User u JOIN u.branch b JOIN u.department d""")
    List<UserBranchDepartDto> findAllUserBranchDepart();
}

