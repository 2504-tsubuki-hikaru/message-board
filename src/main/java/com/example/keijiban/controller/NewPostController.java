package com.example.keijiban.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NewPostController {

    /*
     * 新規投稿画面処理
     */
    @GetMapping("/new")
    public ModelAndView newPost() {
        ModelAndView mav = new ModelAndView();
        // 画面遷移先を指定
        mav.setViewName("/new");
        return mav;
    }
}
