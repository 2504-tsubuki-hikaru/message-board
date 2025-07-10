package com.example.keijiban.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
public class Users {

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

    @Column
    private Integer branchId;

    @Column
    private Integer departmentId;

    @Column
    private Integer isStopped;

    @Column
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime updatedDate;

}
