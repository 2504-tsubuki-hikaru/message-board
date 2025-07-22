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

    //passwordが無い時は更新しないJPQL文
    @Modifying
    @Transactional
    @Query("""
    UPDATE User u
        SET u.account = :#{#user.account},
            u.name = :#{#user.name},
            u.branch.id = :#{#user.branch.id},
            u.department.id = :#{#user.department.id},
            u.isStopped = :#{#user.isStopped},
            u.updatedDate = CURRENT_TIMESTAMP,
            u.password = CASE WHEN :#{#user.password} IS NOT NULL AND TRIM(:#{#user.password}) <> '' 
                          THEN :#{#user.password} 
                          ELSE u.password 
                     END
    WHERE u.id = :#{#user.id}
    """)
    void updateUser(@Param("user") User user);
}
