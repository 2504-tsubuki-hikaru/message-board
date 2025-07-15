package com.example.keijiban.service;

import com.example.keijiban.controller.form.MessageForm;
import com.example.keijiban.repository.MessageRepository;
import com.example.keijiban.repository.entity.Message;
import com.example.keijiban.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;

    //DBへ投稿内容を登録
    public void saveMessage(MessageForm reqMessage) throws ParseException {
        Message saveMessage = setMessageEntity(reqMessage);
        messageRepository.save(saveMessage);
    }

    //引数の情報をEntityに設定
    private Message setMessageEntity(MessageForm reqMessage) throws ParseException {
        Message messages = new Message();
        /*MessageEntityでUser型のuserとuserIdを紐づけているのでUserインスタンスを生成して
        user変数に入れてそこからmessagesにセットする*/
        User user = new User();
        user.setId(reqMessage.getUserId());
        messages.setUser(user);
        messages.setTitle(reqMessage.getTitle());
        messages.setText(reqMessage.getText());
        messages.setCategory(reqMessage.getCategory());
        messages.setCreatedDate(new Date());
        messages.setUpdatedDate(new Date());
        return messages;
    }

    //投稿削除処理
    public void deletePost(Integer id) {
        messageRepository.deleteById(id);
    }
}
