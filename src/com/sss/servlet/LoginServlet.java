package com.sss.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sss.entity.Users;
import com.sss.service.UsersService;
import com.sss.service.impl.UsersServiceImpl;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UsersService userService = new UsersServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        String uno = request.getParameter("uno");
        String password = request.getParameter("password");
        String inputCaptcha = request.getParameter("captcha");

        HttpSession session = request.getSession();
        String sessionCaptcha = (String) session.getAttribute("captchaCode");
        session.removeAttribute("captchaCode");

        // 1. 验证码错误：不计入锁定
        if (inputCaptcha == null || sessionCaptcha == null
                || !inputCaptcha.equalsIgnoreCase(sessionCaptcha)) {
            response.getWriter().write("<script>alert('验证码错误');history.back();</script>");
            return;
        }

        // 2. 先检查账号是否锁定
        String lockMsg = userService.checkAccountLock(uno);
        if (lockMsg != null) {
            response.getWriter().write("<script>alert('" + lockMsg + "');history.back();</script>");
            return;
        }

        // 3. 校验账号密码
        Users user = userService.login(uno, password);
        if (user == null) {
            // 账号密码错误：触发计数和锁定
            userService.handleLoginFail(uno);
            response.getWriter().write("<script>alert('账号或密码错误');history.back();</script>");
            return;
        }

        // 4. 登录成功：清空错误计数和锁定状态
        userService.handleLoginSuccess(uno);
        session.setAttribute("loginUser", user);
        response.sendRedirect("pages/student.html");
    }
}