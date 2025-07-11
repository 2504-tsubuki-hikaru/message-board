package com.example.keijiban.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "messages")
@Getter
@Setter
public class Message {

    @Id
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
}
