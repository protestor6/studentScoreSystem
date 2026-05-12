package com.sss.servlet;

import com.sss.entity.Users;
import com.sss.service.UsersService;
import com.sss.service.impl.UsersServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class GetCurrentUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        Users loginUser = (Users) session.getAttribute("loginUser");
        UsersService userService = new UsersServiceImpl();

        if (loginUser != null) {
            // 构造返回给前端的用户信息
            String json = "{"
                    + "\"uno\":\"" + loginUser.getUno() + "\","
                    + "\"name\":\"" + userService.getUname(loginUser.getUno()) + "\""
                    + "}";
            out.print(json);
        } else {
            out.print("{}");
        }
        out.flush();
        out.close();
    }
}