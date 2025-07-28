package com.example.keijiban.controller;

import com.example.keijiban.controller.form.UserForm;
import com.example.keijiban.controller.form.UserRegistrationForm;
import com.example.keijiban.dto.UserBranchDepartDto;
import com.example.keijiban.dto.UserFilterDto;
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

        UserFilterDto filter = (UserFilterDto)session.getAttribute("Filter");

        if (filter.getBranchId() != 4 && filter.getDepartmentId() != 3) {
            session.setAttribute("notAccess", "無効なアクセスです");
            return new ModelAndView("redirect:/keijiban");
        }

        ModelAndView mav = new ModelAndView();
        //ユーザー情報取得（部署名と支店名を含む）
        List<UserBranchDepartDto> udbData = userBranchDepartService.findAllUserBranchDepart();

        //管理者権限フィルターのエラーメッセージ
        String notAccess = (String)session.getAttribute("notAccess");
        mav.addObject("notAccess", notAccess);

        mav.addObject("notAccess", notAccess);
        mav.addObject("udbData", udbData);
        mav.addObject("filter", filter);
        mav.setViewName("userManagement");

        session.removeAttribute("notAccess");

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

        UserFilterDto filter = (UserFilterDto)session.getAttribute("Filter");

        if (filter.getBranchId() != 4 && filter.getDepartmentId() != 3) {
            session.setAttribute("notAccess", "無効なアクセスです");
            return new ModelAndView("redirect:/keijiban");
        }

        ModelAndView mav = new ModelAndView();
        mav.addObject("formModel", new  UserRegistrationForm());
        mav.setViewName("userRegistration");
        return mav;
    }

    /*
     * ユーザー登録処理
     */
    @PostMapping("/newUser")
    public ModelAndView newUser(@ModelAttribute("formModel") @Validated UserRegistrationForm urForm,
                                BindingResult result) {

        UserFilterDto filter = (UserFilterDto)session.getAttribute("Filter");

        if (filter.getBranchId() != 4 && filter.getDepartmentId() != 3) {
            session.setAttribute("notAccess", "無効なアクセスです");
            return new ModelAndView("redirect:/keijiban");
        }

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
            mav.addObject("accountError", "アカウントが重複しています");
            mav.addObject("formModel", urForm);
            mav.setViewName("/userRegistration");
            return mav;
        }

        //パスワードと確認用パスワードが一致しているかの確認
        if (!urForm.getPassword().equals(urForm.getConfirmPassword())) {
            ModelAndView mav = new ModelAndView();
            mav.addObject("passwordError", "パスワードと確認用パスワードが一致しません");
            mav.addObject("formModel", urForm);
            mav.setViewName("/userRegistration");
            return mav;
        }

        //支社と部署のIDを取り出す。
        String branch = String.valueOf(urForm.getBranchId());
        String depart = String.valueOf(urForm.getDepartmentId());

        //支社と部署の組み合わせに矛盾がないかチェック
        if ((Arrays.asList("1", "2", "3").contains(branch) && Arrays.asList("3", "4").contains(depart)) ||
                (branch.equals("4") && Arrays.asList("1", "2").contains(depart))) {
            ModelAndView mav = new ModelAndView();
            mav.addObject("branchError", "支社と部署の組み合わせが不正です");
            mav.addObject("formModel", urForm);
            mav.setViewName("/userRegistration");
            return mav;
        }

        //ユーザー新規登録
        usersService.saveNewUser(urForm);
        return new ModelAndView("redirect:/management");
    }

    /*
     * ユーザー編集画面遷移処理
     */
    @GetMapping("/userEdit/{id}")
    public ModelAndView userEdit(@PathVariable("id") String idStr) {
        // 数字かどうかをチェック
        if (!idStr.matches("\\d+")) {
            session.setAttribute("notAccess", "不正なパラメーターが入力されました");
            return new ModelAndView("redirect:/management");
        }

        int id = Integer.parseInt(idStr);

        UserFilterDto filter = (UserFilterDto) session.getAttribute("Filter");
        if (filter.getBranchId() != 4 && filter.getDepartmentId() != 3) {
            session.setAttribute("notAccess", "無効なアクセスです");
            return new ModelAndView("redirect:/keijiban");
        }

        //存在しないidかチェック
        UserForm userForm = usersService.findById(id);
        if (userForm == null) {
            session.setAttribute("notAccess", "不正なパラメーターが入力されました");
            return new ModelAndView("redirect:/management");
        }

        ModelAndView mav = new ModelAndView();

        mav.addObject("userDate", userForm);
        mav.addObject("filter", filter);
        mav.setViewName("userEdit");
        return mav;
    }

    //idが無かった場合のエラー処理
    @GetMapping("/userEdit")
    public ModelAndView userEditWithoutId() {
        session.setAttribute("notAccess", "不正なパラメーターが入力されました");
        return new ModelAndView("redirect:/management");
    }

    /*
     * ユーザー編集処理
     */
    @PostMapping("/updateUser/{id}")
    public ModelAndView updateStatus(@RequestParam("id") Integer id, @ModelAttribute("userDate")
                                        @Validated UserForm user, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/userEdit");
            UserFilterDto filter = (UserFilterDto)session.getAttribute("Filter");
            //引数をそのまま返す。
            mav.addObject("formModel", user);
            mav.addObject("userDate", user);
            mav.addObject("filter", filter);
            return mav;
        }

        //accountの重複チェック(JPAメソッドのexistsを使う)
        //存在している場合＝true
        if (usersService.existMyAccount(user.getAccount(), user.getId())) {
            ModelAndView mav = new ModelAndView();
            UserFilterDto filter = (UserFilterDto)session.getAttribute("Filter");
            mav.addObject("accountError", "アカウントが重複しています");
            mav.addObject("formModel", user);
            mav.addObject("userDate", user);
            mav.setViewName("/userEdit");
            mav.addObject("filter", filter);
            return mav;
        }

        //パスワードと確認用パスワードが一致しているかの確認
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            ModelAndView mav = new ModelAndView();
            UserFilterDto filter = (UserFilterDto)session.getAttribute("Filter");
            mav.addObject("passwordError", "パスワードと確認用パスワードが一致しません");
            mav.addObject("formModel", user);
            mav.addObject("userDate", user);
            mav.addObject("filter", filter);
            mav.setViewName("/userEdit");
            return mav;
        }

        //支社と部署のIDを取り出す。
        String branch = String.valueOf(user.getBranchId());
        String depart = String.valueOf(user.getDepartmentId());

        //支社と部署の組み合わせに矛盾がないかチェック
        int branchId = user.getBranchId();
        int departmentId = user.getDepartmentId();

        // 「支社×営業部・技術部」か「本社×総務人事部・情報管理部」以外はエラー
        if (!(// 支社(1〜3)は営業部・技術部のみ許可
                ((branchId >= 1 && branchId <= 3) && (departmentId == 1 || departmentId == 2)) ||
                        // 本社(4)は総務人事部・情報管理部のみ許可
                (branchId == 4 && (departmentId == 3 || departmentId == 4)))) {
            ModelAndView mav = new ModelAndView();
            UserFilterDto filter = (UserFilterDto)session.getAttribute("Filter");
            mav.addObject("branchError", "支社と部署の組み合わせが不正です");
            mav.addObject("formModel", user);
            mav.addObject("userDate", user);
            mav.addObject("filter", filter);
            mav.setViewName("/userEdit");
            return mav;
        }

        user.setId(id);
        usersService.saveEditUser(user);
        return new ModelAndView("redirect:/management");
    }
}
