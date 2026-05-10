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
    private UsersService usersService = new UsersServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 设置编码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        // 获取前端传的账号、密码
        String uno = request.getParameter("uno");
        String password = request.getParameter("password");

        // 登录验证
        Users user = usersService.login(uno, password);

        if (user != null) {
            // 登录成功 → 存入session
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", user);

            // 跳转到学生主页
            response.sendRedirect("student.html");
        } else {
            // 登录失败
            response.getWriter().write("<script>alert('账号或密码错误');history.back();</script>");
        }
    }
}