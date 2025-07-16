package com.example.keijiban.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String account;

    @Column
    private String password;

    @Column
    private String name;

    //usersテーブルにはbranch_id(外部キー)があるのでuserEntityに
    // @ManyToOne @JoinColumnをつけて紐づける必要がある。
    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @Column
    private Integer isStopped;

    @Column
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime updatedDate;

}

