package com.example.keijiban.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserMessageDto {

    private Integer messageId;
    private String text;
    private String category;
    private Integer userId;
    private String userName;
    private String userAccount;

    public UserMessageDto(Integer messageId, String text, String category,
                          Integer userId, String userName, String userAccount) {
        this.messageId = messageId;
        this.text = text;
        this.category = category;
        this.userId = userId;
        this.userName = userName;
        this.userAccount = userAccount;
    }
}
