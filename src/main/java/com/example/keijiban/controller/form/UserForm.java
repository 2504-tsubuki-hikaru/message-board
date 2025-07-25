package com.example.keijiban.controller.form;

import com.example.keijiban.validation.CreateGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class UserForm implements Serializable {

    private Integer Id;

    @NotBlank(message="アカウントを入力してください")
    @Pattern(regexp = "^$|^[a-zA-Z0-9]{6,20}$",message ="アカウントは半角英数字かつ6文字以上20文字以下で入力してください")
    private String account;

    @Pattern(regexp = "^$|^[a-zA-Z0-9]{6,20}$",message ="パスワードは半角英数字かつ6文字以上20文字以下で入力してください")
    private String password;

    @Pattern(regexp = "^$|^[a-zA-Z0-9]{6,20}$",message ="パスワードは半角英数字かつ6文字以上20文字以下で入力してください")
    private String confirmPassword;

    @NotBlank(message = "氏名を入力してください")
    @Size(max = 10,message="氏名は10文字以下で入力してください")
    private String name;

    @NotNull(message = "支社を選択してください")
    private Integer branchId;

    @NotNull(message = "部署を選択してください")
    private Integer departmentId;

    private int isStopped;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updatedDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdDate;

    private Integer UserId;
}








