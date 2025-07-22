package com.example.keijiban.service;

import com.example.keijiban.controller.form.UserForm;
import com.example.keijiban.controller.form.UserRegistrationForm;
import com.example.keijiban.repository.UsersRepository;
import com.example.keijiban.repository.entity.Branch;
import com.example.keijiban.repository.entity.Department;
import com.example.keijiban.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    //idに該当するデータを１件取得
    public UserForm findById(Integer id) {
        List<User> results = new ArrayList<>();
        results.add((User) usersRepository.findById(id).orElse(null));
        List<UserForm> reports = setUserForm(results);
        return reports.get(0);
    }

    //取得したデータをUserFormに詰め替え
    private List<UserForm> setUserForm(List<User> results) {
        List<UserForm> users = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            UserForm user = new UserForm();
            User result = results.get(i);
            user.setId(result.getId());
            user.setAccount(result.getAccount());
            user.setPassword("");
            user.setName(result.getName());
            user.setIsStopped(result.getIsStopped());
            user.setCreatedDate(result.getCreatedDate());
            user.setBranchId(result.getBranch() != null ? result.getBranch().getId() : null);
            user.setDepartmentId(result.getDepartment() != null ? result.getDepartment().getId() : null);
            users.add(user);
        }
        return users;
    }

    //ユーザー情報編集
    public void saveEditUser(UserForm editUser) {
        User user = setEditUserEntity(editUser);
        usersRepository.updateUser(user);

    }

    public User setEditUserEntity(UserForm editUser) {
        User users = new User ();
        //User(Entity)でjoinColumしているため空箱を作って引数から値を取得
        Branch branch = new Branch();
        branch.setId(editUser.getBranchId());
        Department department = new Department();
        department.setId(editUser.getDepartmentId());

        //userEntityに詰める。
        users.setBranch(branch);
        users.setDepartment(department);
        //その他の詰め替え。
        users.setId(editUser.getId());
        users.setAccount(editUser.getAccount());
        users.setName(editUser.getName());
        users.setIsStopped(editUser.getIsStopped());
        users.setCreatedDate(editUser.getCreatedDate());
        users.setUpdatedDate(new Date());
        users.setCreatedDate(editUser.getCreatedDate());

        String pass = editUser.getPassword();
        if (pass != null && !pass.isEmpty()) {
            users.setPassword(pass);
        }
        return users;
    }
}
