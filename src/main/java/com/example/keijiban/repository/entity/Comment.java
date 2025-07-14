package com.example.keijiban.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "comments")
@Getter
@Setter
public class Comment {

    @Id
    private Integer id;

    @Column
    private String text;

    @ManyToOne
    //結合先テーブルのプライマリキー(主キー）となっているカラムを指定する）。
    @JoinColumn(name = "user_id")
    private User user;

    //commentsテーブルにはすでにあるけど下記のアノテーションで関連付けることが必要になる。
    //上のuserIdも同じ理由
    @ManyToOne
    @JoinColumn(name = "message_id")
    private Message message;

    @Column
    private Date createdDate;

}
