package com.example.keijiban.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserBranchDepartDto {

    //Usersテーブル
    private Integer id;
    private String account;
    private String name;
    private Integer branchId;
    private Integer departmentId;
    private Integer isStopped;

    //branchesテーブル
    private String branchName;

    //departmentsテーブル
    private String departmentName;

    //JPQl用のコンストラクタ
    public UserBranchDepartDto(Integer id, String account, String name, Integer branchId,
                               Integer departmentId, Integer isStopped, String branchName,
                               String departmentName) {

    this.id = id;
    this.account = account;
    this.name = name;
    this.branchId = branchId;
    this.departmentId = departmentId;
    this.isStopped = isStopped;
    this.branchName = branchName;
    this.departmentName = departmentName;

    }

}
