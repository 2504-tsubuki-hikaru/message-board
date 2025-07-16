package com.example.keijiban.service;

import com.example.keijiban.controller.form.CommentForm;
import com.example.keijiban.repository.CommentRepository;
import com.example.keijiban.repository.entity.Comment;
import com.example.keijiban.repository.entity.Message;
import com.example.keijiban.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    //コメント登録処理
    public void commentAdd(CommentForm commentForm) {
        Comment comments = setCommentEntity(commentForm);
        commentRepository.save(comments);
    }

    //commentFormをComments(entity)に詰め替え。
    private Comment setCommentEntity(CommentForm commentForm) {
        Comment comments = new Comment();

        // IDだけ設定されたUserとmessageオブジェクトを作成。
        User user = new User();
        user.setId(commentForm.getUserId()); // FormのuserIdからセット
        Message message = new Message();
        message.setId(commentForm.getMessageId()); // FormのmessageIdからセット

        // Commentsエンティティに関連付け
        comments.setUser(user);
        comments.setMessage(message);
        //その他の詰め替え。
        comments.setId(commentForm.getId());
        comments.setText(commentForm.getText());
        comments.setCreatedDate(new Date());
        comments.setUpdatedDate(new Date());
        return comments;
    }

    //コメント削除
    public void commentDeleteById(Integer id) {
        commentRepository.deleteById(id);
    }
}
