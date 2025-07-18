package com.example.keijiban.controller;

import com.example.keijiban.cipher.CipherUtil;
import com.example.keijiban.controller.form.LoginForm;
import com.example.keijiban.repository.entity.User;
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

@Controller
public class LoginController {

   @Autowired
    UsersService usersService;

    @Autowired
    HttpSession session;

   @GetMapping("/login")
   public ModelAndView login() {
       ModelAndView mav = new ModelAndView("login");
       mav.addObject("formModel", new LoginForm());
       session.invalidate();
       return mav;
   }

   @PostMapping("/loginProcess")
    public ModelAndView loginProcess (@ModelAttribute("formModel") @Validated LoginForm loginForm, BindingResult result) {
        ModelAndView mav = new ModelAndView();
        if (result.hasErrors()) {
            //エラーの時はログイン画面でエラーを出したいから遷移先を指定
            mav.setViewName("login");
            //引数をそのまま返す。
            mav.addObject("formModel", loginForm);
            return mav;
        }
       String account = loginForm.getAccount();
       String password = loginForm.getPassword();
       //パスワードを暗号化
       String encPassword = CipherUtil.encrypt(password);
       //ユーザー情報を取得
       User users = usersService.findByAccountAndPassword(account, encPassword);

       //ユーザー情報が取得できなかった時、もしくは活動停止アカウントの時はエラーを表示
       if (users == null || users.getIsStopped() !=0) {
           mav.addObject("formModel", loginForm);
           mav.addObject("errorMessages", "ログインに失敗しました");
           mav.setViewName("/login");
           return mav;
       }
       loginForm.setUserId(users.getId());
       loginForm.setIsStopped(users.getIsStopped());
       //ログイン情報をセッションに入れて、ホーム画面にリダイレクト
       session.setAttribute("loginUser", loginForm);
       return new ModelAndView("redirect:/home");
    }
}
