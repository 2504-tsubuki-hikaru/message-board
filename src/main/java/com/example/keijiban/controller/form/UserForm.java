package com.example.keijiban.controller.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserForm implements Serializable {

    @NotBlank(message="アカウントを入力してください")
    private String account;

    @NotBlank(message="パスワードを入力してください")
    private String password;

    private int isStopped;

}
