package com.example.keijiban.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserCommentDto {

    //commentsテーブル
    private int id;
    private String text;
    private int userId;
    private int messageId;
    private Date createdDate;

    //usersテーブル
    private String name;
    private String account;

    // JPQL用コンストラクタ
    public UserCommentDto(int id, String text, int userId, int messageId,
                          Date createdDate, String name, String account) {

        this.id = id;
        this.text = text;
        this.userId = userId;
        this.messageId = messageId;
        this.createdDate = createdDate;
        this.name = name;
        this.account = account;
    }
}
