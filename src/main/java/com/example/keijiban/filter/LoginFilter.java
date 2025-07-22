package com.example.keijiban.filter;

import com.example.keijiban.repository.entity.User;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        User loginUser = (session != null) ? (User) session.getAttribute("loginUser") : null;

        if (loginUser != null) {
            chain.doFilter(request, response);
        } else {
            if (session == null) {
                session = req.getSession(true); // セッションがない場合は新規作成
            }
            session.setAttribute("errorMessages", "ログインしてください");
            res.sendRedirect("/login");
            return; // リダイレクト後は後続処理しない
        }
    }

    @Override
    public void init(FilterConfig config) {}

    @Override
    public void destroy() {}
}