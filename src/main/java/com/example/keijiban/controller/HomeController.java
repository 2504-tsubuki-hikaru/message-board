package com.example.keijiban.controller;

import com.example.keijiban.dto.UserMessageDto;
import com.example.keijiban.controller.form.UserForm;
import com.example.keijiban.service.UserMessageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    HttpSession session;

    @Autowired
    UserMessageService userMessageService;

    @GetMapping("/")
    public ModelAndView top() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("forward:/login");
        return mav;
    }

    @GetMapping("/home")
    public ModelAndView home() {
        ModelAndView mav = new ModelAndView();
        List<UserMessageDto> userMessageData = userMessageService.findByUserMessages();
        UserForm loginUser = (UserForm)session.getAttribute("loginUser");
        mav.addObject("UserMessageDate", userMessageData);
        mav.setViewName("home");
        return mav;
    }
}
