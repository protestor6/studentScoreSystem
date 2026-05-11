package com.sss.filter;

import com.sss.entity.UserType;
import com.sss.entity.Users;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession();
        String uri = request.getRequestURI();
        String ctx = request.getContextPath();

        Users loginUser = (Users) session.getAttribute("loginUser");

        // ==============================================
        // 已登录 → 访问 根路径 / 或 login.html → 都跳学生页
        // ==============================================
        boolean isRootPath = uri.equals(ctx + "/");
        boolean isLoginPage = uri.endsWith("login.html");

        if (loginUser != null) {
            if (isRootPath || isLoginPage) {
            	if(loginUser.getUtype()==UserType.学生)
                	response.sendRedirect(ctx + "/pages/student.html");
                return;
            }
        }

        // ==============================================
        // 未登录 → 访问根路径 / → 直接显示登录页
        // ==============================================
        if (loginUser == null) {
            if (isRootPath) {
                request.getRequestDispatcher("/login.html").forward(request, response);
                return;
            }
        }

        // ==============================================
        // 放行不需要登录的资源
        // ==============================================
        boolean isStatic = uri.contains("/css/") || uri.contains("/js/") || uri.contains("/img/");
        boolean isLoginServlet = uri.endsWith("login");
        boolean isLogout = uri.endsWith("logout");
        boolean isCaptcha = uri.contains("captcha");

        if (isStatic || isLoginPage || isLoginServlet || isLogout || isCaptcha) {
            chain.doFilter(request, response);
            return;
        }

        // ==============================================
        // 未登录访问其他页面 → 跳登录
        // ==============================================
        if (loginUser == null) {
            response.sendRedirect(ctx + "/login.html");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}
    @Override
    public void destroy() {}
}