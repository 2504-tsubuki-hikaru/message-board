package com.example.keijiban.repository;

import com.example.keijiban.dto.UserFilterDto;
import com.example.keijiban.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFilterRepository extends JpaRepository<User, Integer> {
    @Query("""
    SELECT new com.example.keijiban.dto.UserFilterDto(
        u.id, u.account, u.password, u.name,
        u.branch.id, u.department.id,
        u.isStopped, u.createdDate, u.updatedDate
    )
    FROM User u
    WHERE u.id = :id
""")
    UserFilterDto findUserDtoById(@Param("id") int id);
}
