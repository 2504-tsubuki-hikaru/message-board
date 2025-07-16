package com.example.keijiban.controller;

import com.example.keijiban.dto.UserBranchDepartDto;
import com.example.keijiban.service.UserBranchDepartService;
import com.example.keijiban.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class UserManagementController {
    @Autowired
    UserBranchDepartService userBranchDepartService;

    @Autowired
    UsersService usersService;

    /*
     * ユーザー管理画面遷移処理
     */
    @GetMapping("/management")
    public ModelAndView newPost() {

        ModelAndView mav = new ModelAndView();
        //ユーザー情報取得（部署名と支店名を含む）
        List<UserBranchDepartDto> udbData = userBranchDepartService.findAllUserBranchDepart();

        mav.addObject("udbData", udbData);
        mav.setViewName("management");

        return mav;
    }

    /*
     * ユーザー復活・停止処理
     */
    @PutMapping("/update/{id}")
    public ModelAndView updateStatus( @RequestParam("id") Integer id,
                                      @RequestParam("isStopped") Integer isStopped) {

        usersService.updateIsStopped(id, isStopped);
        return new ModelAndView("redirect:/management");
    }
}
