package com.example.keijiban.controller.form;

import com.example.keijiban.repository.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class MessageForm implements Serializable {

    private int userId;

    @NotBlank(message="件名を入力してください")
    @Size(max = 30, message ="件名は30文字以内で入力してください")
    private String title;

    @NotBlank(message="本文を入力してください")
    @Size(max = 1000, message ="本文は1000文字以内で入力してください")
    private String text;

    @NotBlank(message="カテゴリを入力してください")
    @Size(max = 10, message ="カテゴリは10文字以内で入力してください")
    private String category;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updatedDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdDate;

}
