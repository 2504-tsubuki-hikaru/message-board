package com.example.keijiban.controller;

import com.example.keijiban.controller.form.UserRegistrationForm;
import com.example.keijiban.dto.UserBranchDepartDto;
import com.example.keijiban.service.UserBranchDepartService;
import com.example.keijiban.service.UsersService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

@Controller
public class UserManagementController {
    @Autowired
    UserBranchDepartService userBranchDepartService;

    @Autowired
    UsersService usersService;

    @Autowired
    HttpSession session;

    /*
     * ユーザー管理画面遷移処理
     */
    @GetMapping("/management")
    public ModelAndView newPost() {

        ModelAndView mav = new ModelAndView();
        //ユーザー情報取得（部署名と支店名を含む）
        List<UserBranchDepartDto> udbData = userBranchDepartService.findAllUserBranchDepart();

        mav.addObject("udbData", udbData);
        mav.setViewName("userManagement");

        return mav;
    }

    /*
     * ユーザー復活・停止処理
     */
    @PutMapping("/update/{id}")
    public ModelAndView updateStatus(@RequestParam("id") Integer id,
                                     @RequestParam("isStopped") Integer isStopped) {

        usersService.updateIsStopped(id, isStopped);
        return new ModelAndView("redirect:/management");
    }

    /*
     * ユーザー登録画面遷移処理
     */
    @GetMapping("/registration")
    public ModelAndView userRegistration() {
        ModelAndView mav = new ModelAndView();
        //ユーザー情報取得（部署名と支店名を含む）
        //List<UserBranchDepartDto> udbData = userBranchDepartService.findAllUserBranchDepart();
        //mav.addObject("udbData", udbData);
        mav.setViewName("userRegistration");
        return mav;
    }

    /*
     * ユーザー登録処理
     */
    @PostMapping("/newUser")
    public ModelAndView newUser(@ModelAttribute("formModel") @Validated UserRegistrationForm urForm,
                                BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/userRegistration");
            //引数をそのまま返す。
            mav.addObject("formModel", urForm);
            return mav;
        }

        //accountの重複チェック(JPAメソッドのexistsを使う)
        //存在している場合＝true
        if (usersService.existsByAccount(urForm.getAccount())) {
            ModelAndView mav = new ModelAndView();
            mav.addObject("errorMessages", "アカウントが重複しています");
            mav.setViewName("/userRegistration");
            return mav;
        }

        //パスワードと確認用パスワードが一致しているかの確認
        if (!urForm.getPassword().equals(urForm.getConfirmPassword())) {
            ModelAndView mav = new ModelAndView();
            mav.addObject("errorMessages", "パスワードと確認用パスワードが一致しません");
            mav.setViewName("/userRegistration");
            return mav;
        }

        //支社と部署の組み合わせに矛盾がないかチェック
        String branch = String.valueOf(urForm.getBranchId());
        String dept = String.valueOf(urForm.getDepartmentId());

        if (Arrays.asList("1","2","3").contains(branch) && Arrays.asList("3","4").contains(dept)) {
            ModelAndView mav = new ModelAndView();
            mav.addObject("errorMessages", "支社と部署の組み合わせが不正です");
            mav.setViewName("/userManagement");
            return mav;
            }

        //ユーザー新規登録
        usersService.saveNewUser(urForm);
        return new ModelAndView("redirect:/management");
    }
}
