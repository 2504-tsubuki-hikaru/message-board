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

    //account重複チェック
    boolean existsByAccount(String account);

    //passwordがnullだった時に対応する編集登録
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.account = :account, u.name = :name, u.branch.id = :branchId, u.department.id = :departmentId, u.isStopped = :isStopped, u.updatedDate = CURRENT_TIMESTAMP, u.password = :password WHERE u.id = :id")
    void updateUser(
            @Param("id") Integer id,
            @Param("account") String account,
            @Param("name") String name,
            @Param("branchId") Integer branchId,
            @Param("departmentId") Integer departmentId,
            @Param("isStopped") Boolean isStopped,
            @Param("password") String password
    );
}