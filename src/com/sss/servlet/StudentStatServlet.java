//made by 叶永盛
package com.sss.servlet;
import com.google.gson.Gson;
import com.sss.entity.StudentStat;
import com.sss.entity.Users;
import com.sss.service.StudentStatService;
import com.sss.service.impl.StudentStatServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class StudentStatServlet extends HttpServlet {
    private StudentStatService studentStatService = new StudentStatServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();

        // 获取当前登录用户
        HttpSession session = request.getSession();
        Users loginUser = (Users) session.getAttribute("loginUser");
        if (loginUser == null) {
            out.print("{}");
            return;
        }
        String uno = loginUser.getUno();
        System.out.println("当前登录用户学号：" + uno);

        // 调用服务
        StudentStat stat = studentStatService.getStudentStat(uno);
        out.print(new Gson().toJson(stat));
        out.flush();
        out.close();
    }
}