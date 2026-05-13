package com.sss.servlet;

import com.sss.entity.StudentScoreDetail;
import com.sss.entity.Users;
import com.sss.service.StudentScoreService;
import com.sss.service.impl.StudentScoreServiceImpl;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class StudentScoreQueryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private StudentScoreService StudentScoreService = new StudentScoreServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();

        // 1. 获取当前登录用户
        HttpSession session = request.getSession();
        Users loginUser = (Users) session.getAttribute("loginUser");
        if (loginUser == null) {
            out.print("[]");
            out.flush();
            return;
        }
        String uno = loginUser.getUno();

        // 2. 获取查询参数
        String term = request.getParameter("term");
        String courseName = request.getParameter("courseName");
        String courseCode = request.getParameter("courseCode");
        String sort = request.getParameter("sort"); // asc/desc/null

        // 3. 调用DAO查询
        List<StudentScoreDetail> scoreList = StudentScoreService.queryScores(uno, term, courseName, courseCode, sort);

        // 4. 转成JSON返回给前端
        Gson gson = new Gson();
        out.print(gson.toJson(scoreList));
        out.flush();
        out.close();
    }
}