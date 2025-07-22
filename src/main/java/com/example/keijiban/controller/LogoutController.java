package com.example.keijiban.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LogoutController {

    @Autowired
    HttpSession session;

    @GetMapping("/logout")
    public ModelAndView logout() {

        // セッションの無効化
        session.invalidate();
        return new ModelAndView("redirect:/login");
    }
}
