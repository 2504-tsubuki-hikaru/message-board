package com.example.keijiban.controller;

import com.example.keijiban.cipher.CipherUtil;
import com.example.keijiban.controller.form.UsersForm;
import com.example.keijiban.repository.entity.Users;
import com.example.keijiban.service.UsersService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {

   @Autowired
    UsersService usersService;

    @Autowired
    HttpSession session;

   @GetMapping("/login")
   public ModelAndView login() {
       ModelAndView mav = new ModelAndView("login");
       mav.addObject("formModel", new UsersForm());
       return mav;
   }

   @PostMapping("/loginProcess")
    public ModelAndView loginProcess (@ModelAttribute("formModel") @Validated UsersForm usersForm, BindingResult result) {
        ModelAndView mav = new ModelAndView();
        if (result.hasErrors()) {
            //エラーの時はログイン画面でエラーを出したいから遷移先を指定
            mav.setViewName("login");
            //引数をそのまま返す。
            mav.addObject("formModel", usersForm);
            return mav;
        }
       String account = usersForm.getAccount();
       String password = usersForm.getPassword();

       String encPassword = CipherUtil.encrypt(password);

        Users users = usersService.findByAccountAndPassword(account, encPassword);

       if (users == null) {
           List<String> errorMessages = new ArrayList<>();
           errorMessages.add("ログインに失敗しました");
           session.setAttribute("errorMessages", errorMessages);
           mav.setViewName("forward:/login");
       }
        //Usersの中にあるisStoppedの値の中身を確かめたいのでgetする。
       Integer isStopped = users.getIsStopped();

       if (isStopped != 0) {
           List<String> errorMessages = new ArrayList<>();
           errorMessages.add("ログインに失敗しました");
           session.setAttribute("errorMessages", errorMessages);
           mav.setViewName("forward:/login");
        }

       return new ModelAndView("redirect:/home");
    }
}
