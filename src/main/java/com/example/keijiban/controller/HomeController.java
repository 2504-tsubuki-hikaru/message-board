package com.example.keijiban.controller;

import com.example.keijiban.controller.form.UserForm;
import com.example.keijiban.dto.UserCommentDto;
import com.example.keijiban.dto.UserMessageDto;
import com.example.keijiban.service.UserCommentService;
import com.example.keijiban.service.UserMessageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    HttpSession session;

    @Autowired
    UserMessageService userMessageService;

    @Autowired
    UserCommentService userCommentService;

    @GetMapping("/")
    public ModelAndView top() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("forward:/login");
        return mav;
    }

    @GetMapping("/home")
    public ModelAndView home(@RequestParam(name="start",required = false)String start,
                             @RequestParam(name="end",required = false)String end,
                             @RequestParam(name="category",required = false) String category) throws ParseException {
        ModelAndView mav = new ModelAndView();

        //絞込機能の検索履歴用
        mav.addObject("start", start);
        mav.addObject("end", end);
        mav.addObject("category", category);

        //投稿取得
        List<UserMessageDto> userMessageData = userMessageService.findByUserMessages(start, end, category);
        //コメント取得
        List<UserCommentDto> userCommentData = userCommentService.getAllUserComments();
        //ログインユーザー情報
        UserForm loginUser = (UserForm)session.getAttribute("loginUser");

        //取得したデータをmavにセットしhtmlで使えるように
        mav.addObject("userMessageDate", userMessageData);
        mav.addObject("userCommentDate", userCommentData);
        mav.setViewName("home");
        return mav;
    }
}
