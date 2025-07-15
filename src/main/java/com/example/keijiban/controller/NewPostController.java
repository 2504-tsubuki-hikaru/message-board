package com.example.keijiban.controller;

import com.example.keijiban.controller.form.MessageForm;
import com.example.keijiban.controller.form.UserForm;
import com.example.keijiban.service.MessageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;

@Controller
public class NewPostController {

    @Autowired
    MessageService messageService;

    @Autowired
    HttpSession session;

    /*
     * 新規投稿画面処理
     */
    @GetMapping("/new")
    public ModelAndView newPost() {
        ModelAndView mav = new ModelAndView();
        // 画面遷移先を指定
        mav.setViewName("/new");
        mav.addObject("formModel", new MessageForm());
        return mav;
    }

    /*
     * 新規投稿処理
     */
    @PostMapping("/add")
    public ModelAndView addPost(@ModelAttribute("formModel") @Validated MessageForm messageForm, BindingResult result) throws ParseException {
        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            //エラーの時は新規投稿画面でエラーを出したいから遷移先を指定
            mav.setViewName("/new");
            //引数をそのまま返す。
            mav.addObject("formModel", messageForm);
            return mav;
        }
        //ログインユーザー情報を取得
        UserForm loginUser = (UserForm)session.getAttribute("loginUser");
        //userIdをmessageFormに格納
        messageForm.setUserId(loginUser.getUserId());
        messageService.saveMessage(messageForm);
        return new ModelAndView("redirect:/home");
    }
}
