package com.example.keijiban.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserMessageDto {

    /*messagesテーブルの情報
    JPQLでカラム名と名前が一致していればいいからmessageIdなんてカラム名はmessageテーブルには無いけど
    ここでは分かりやすい名前で記載している。*/
    private int messageId;
    private String text;
    private int userId;
    private String category;
    private Date createdDate;
    private String title;

    // usersテーブルの情報
    private String name;
    private String account;

    // JPQL用コンストラクタ
    public UserMessageDto(int messageId, String text, int userId, String category, Date createdDate,
                          String title, String name, String account) {
        this.messageId = messageId;
        this.text = text;
        this.userId = userId;
        this.category = category;
        this.createdDate = createdDate;
        this.title = title;
        this.name = name;
        this.account = account;

    }
}
