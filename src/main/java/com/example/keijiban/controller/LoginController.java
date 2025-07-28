package com.example.keijiban.controller;

import com.example.keijiban.cipher.CipherUtil;
import com.example.keijiban.controller.form.LoginForm;
import com.example.keijiban.dto.UserFilterDto;
import com.example.keijiban.repository.entity.User;
import com.example.keijiban.service.UserFilterService;
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
    UserFilterService userFilterService;

    @GetMapping("/login")
    public ModelAndView login(HttpSession session) {
        ModelAndView mav = new ModelAndView("login");
        mav.addObject("formModel", new LoginForm());
        String errorMessages = (String)session.getAttribute("errorMessages");
        mav.addObject("errorMessages", errorMessages);
        session.removeAttribute("errorMessages");
        return mav;
    }

    @PostMapping("/loginProcess")
    public ModelAndView loginProcess(@ModelAttribute("formModel") @Validated LoginForm loginForm,
                                     BindingResult result,
                                     HttpSession session) {
        ModelAndView mav = new ModelAndView();
        if (result.hasErrors()) {
            mav.setViewName("login");
            mav.addObject("formModel", loginForm);
            return mav;
        }

        String account = loginForm.getAccount();
        String password = loginForm.getPassword();
        String encPassword = CipherUtil.encrypt(password);

        User users = usersService.findByAccountAndPassword(account, encPassword);

        if (users == null || users.getIsStopped() != 0) {
            mav.addObject("formModel", loginForm);
            mav.addObject("errorMessages", "ログインに失敗しました");
            mav.setViewName("login");
            return mav;
        }

        //管理フィルターように再検索(branchIdとdepartmentIdが取得できていないから)
        //上のUser型のfindByAccountAndPasswordは下のやつに変えてもいいかも
        int id = users.getId();
        UserFilterDto user =  userFilterService.findById(id);

        session.setAttribute("Filter", user);
        session.setAttribute("loginUser", users);
        return new ModelAndView("redirect:/keijiban");
    }
}
