package com.example.keijiban.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserFilterDto {

    //Userテーブルのみ
    private int id;
    private String account;
    private String password;
    private String name;
    private Integer branchId;
    private Integer departmentId;
    private Integer isStopped;
    private Date createdDate;
    private Date updatedDate;

    public UserFilterDto(int id, String account, String password, String name, Integer branchId,
                        Integer departmentId, Integer isStopped, Date createdDate, Date updatedDate) {

        this.id = id;
        this.account = account;
        this.password = password;
        this.name = name;
        this.branchId = branchId;
        this.departmentId = departmentId;
        this.isStopped = isStopped;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;

    }
}
