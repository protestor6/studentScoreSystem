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

        // 拿到登录用户
        Users loginUser = (Users) session.getAttribute("loginUser");

        // ========== 放行无需登录的资源 ==========
        boolean isStatic = uri.contains("/css/") 
                        || uri.contains("/js/") 
                        || uri.contains("/img/");
        boolean isLoginPage = uri.endsWith("login.html");
        boolean isLoginServlet = uri.endsWith("login");
        boolean isCaptcha = uri.contains("captcha");

        if (isStatic || isLoginPage || isLoginServlet || isCaptcha) {
            chain.doFilter(request, response);
            return;
        }

        // ========== 未登录拦截 ==========
        if (loginUser == null) {
            response.sendRedirect(ctx + "/login.html");
            return;
        }

        // 已登录正常放行
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}
    @Override
    public void destroy() {}
}