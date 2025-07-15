package com.example.keijiban.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "messages")
@Getter
@Setter
public class Message {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String title;

    @Column
    private String text;

    @Column
    private String category;

    @ManyToOne
    //結合先テーブルのプライマリキー(主キー）となっているカラムを指定する）。
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private Date createdDate;

    @Column
    private Date updatedDate;
}
