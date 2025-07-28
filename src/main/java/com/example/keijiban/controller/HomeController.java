package com.example.keijiban.controller;

import com.example.keijiban.controller.form.CommentForm;
import com.example.keijiban.dto.UserCommentDto;
import com.example.keijiban.dto.UserMessageDto;
import com.example.keijiban.repository.entity.User;
import com.example.keijiban.service.CommentService;
import com.example.keijiban.service.MessageService;
import com.example.keijiban.service.UserCommentService;
import com.example.keijiban.service.UserMessageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    HttpSession session;

    @Autowired
    UserMessageService userMessageService;

    @Autowired
    UserCommentService userCommentService;

    @Autowired
    MessageService messageService;

    @Autowired
    CommentService commentService;

    @GetMapping("/")
    public ModelAndView top() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("forward:/login");
        return mav;
    }

    /*
     * ホーム画面表示処理
     */
    @GetMapping("/keijiban")
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
        User loginUser = (User)session.getAttribute("loginUser");
        //バリデーションのエラーメッセージをsessionから取得(コメント投稿)
        List<String> errorMessages = (List<String>)session.getAttribute("errorMessages");
        //管理者権限フィルターのエラーメッセージ
        String notAccess = (String)session.getAttribute("notAccess");
        mav.addObject("notAccess", notAccess);
        //int型にすると!=nullが使えないのでIntegerを使う（ラッパークラス）
        Integer messageId = (Integer) session.getAttribute("messageId");

        //初期表示のエラー回避
        if (messageId != null) {
            mav.addObject("messageId",messageId);
        }

        //取得したデータをmavにセットしhtmlで使えるように
        mav.addObject("userMessageDate", userMessageData);
        mav.addObject("userCommentDate", userCommentData);
        mav.addObject("errorMessages", errorMessages);
        mav.addObject("notAccess", notAccess);
        mav.addObject("loginUser", loginUser);
        mav.setViewName("home");

        //セッションの破棄(エラーメッセージが残ってしまうため)
        session.removeAttribute("errorMessages");
        session.removeAttribute("userId");
        session.removeAttribute("notAccess");

        return mav;
    }

    /*
     * 投稿削除処理
     */
    @DeleteMapping("/delete/{id}")
    //@PathVariable = /delete/{id}の{id}をdeleteContentメソッドの引数として渡す役割。
    public ModelAndView deletePost(@PathVariable Integer id) {
        //投稿を削除
        messageService.deletePost(id);
        //削除後、新規投稿全件取得をしたい
        return new ModelAndView("redirect:/keijiban");
    }

    /*
     * コメント投稿処理
     */
    @PostMapping("/commentAdd")
    public ModelAndView addComment(@Validated @ModelAttribute("formModel") CommentForm commentForm, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            //resultからgetFieldErrors()でエラーメッセージをとってこれる。
            for(FieldError error : result.getFieldErrors()) {
                errorMessages.add(error.getDefaultMessage());
            }

            session.setAttribute("errorMessages",errorMessages );
            session.setAttribute("messageId",commentForm.getMessageId());

            ModelAndView mav = new ModelAndView();
            //エラーの時はホーム画面でエラーを出したいから遷移先を指定
            mav.setViewName("redirect:/keijiban");
            //引数をそのまま返す。
            mav.addObject("formModel", commentForm);
            return mav;
        }
        //ここでログインユーザーのIDをとっていないから最初にログインした奴のuserIdでDB登録されていた。
        User user = (User)session.getAttribute("loginUser");
        //userIdをmessageFormに格納
        commentForm.setUserId(user.getId());
        //コメント内容をDBへ登録
        commentService.commentAdd(commentForm);
        return new ModelAndView("redirect:/keijiban");
    }

    /*
     * コメント削除処理
     */
    @DeleteMapping("/commentDelete/{id}")
    public ModelAndView deleteComment(@PathVariable Integer id) {
        // 投稿をテーブルに格納
        commentService.commentDeleteById(id);
        // rootへリダイレクト
        return new ModelAndView("redirect:/keijiban");
    }
}
