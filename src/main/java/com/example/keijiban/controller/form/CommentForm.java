package com.example.keijiban.controller.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class CommentForm implements Serializable  {

    private Integer id;

    @NotBlank(message="メッセージを入力してください")
    @Size(max = 500, message ="本文は500文字以内で入力してください")
    private String text;

    private Integer userId;

    private Integer messageId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updatedDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdDate;

}
