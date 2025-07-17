package com.example.keijiban.service;

import com.example.keijiban.controller.form.UserRegistrationForm;
import com.example.keijiban.repository.UsersRepository;
import com.example.keijiban.repository.entity.Branch;
import com.example.keijiban.repository.entity.Department;
import com.example.keijiban.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UsersService {

    @Autowired
    UsersRepository usersRepository;

    public User findByAccountAndPassword(String account, String password) {
        return usersRepository.findByAccountAndPassword(account, password);
    }

    //ユーザー活動・停止の更新処理
    public void updateIsStopped(Integer id, Integer isStopped) {
        usersRepository.updateIsStopped(id,isStopped);
    }

    //account名重複チェック
    public boolean existsByAccount(String account) {
        return usersRepository.existsByAccount(account);
    }

    //新規ユーザー登録処理
    public void saveNewUser(UserRegistrationForm reqNewUser) {
        User saveuser = setNewUserEntity(reqNewUser);
        usersRepository.save(saveuser);
    }

    //ユーザー新規登録をする為にEntityへ詰め替えている。
    public User setNewUserEntity(UserRegistrationForm reqNewUser) {

        User users = new User ();
        //User(Entity)でjoinColumしているため空箱を作って引数から値を取得
        Branch branch = new Branch();
        branch.setId(reqNewUser.getBranchId());
        Department department = new Department();
        department.setId(reqNewUser.getDepartmentId());

        //userEntityに詰める。
        users.setBranch(branch);
        users.setDepartment(department);
        //その他の詰め替え。
        users.setAccount(reqNewUser.getAccount());
        users.setPassword(reqNewUser.getPassword());
        users.setName(reqNewUser.getName());
        users.setIsStopped(reqNewUser.getIsStopped());
        //UserEntityをLocalDateからDate型に変更したから変なエラーになったらチェックする。
        users.setCreatedDate(new Date());
        users.setUpdatedDate(new Date());
        return users;
    }
}
