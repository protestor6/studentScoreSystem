//made by 叶永盛
package com.sss.servlet;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sss.dao.UsersDao;
import com.sss.dao.impl.UsersDaoImpl;
import com.sss.entity.Users;
import com.sss.service.UsersService;
import com.sss.service.impl.UsersServiceImpl;
import com.sss.util.XssUtil;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UsersService userService = new UsersServiceImpl();
    private UsersDao usersDao = new UsersDaoImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        response.setHeader("X-XSS-Protection", "1;mode=block");//防XSS脚本攻击
        response.setHeader("X-Content-Type-Options", "nosniff");//防MIME嗅探
        response.setHeader("X-Frame-Options", "DENY");//防iframe嵌套
        response.setHeader("Content-Security-Policy", "default-src 'self'");//防非法脚本执行
        
        String uno = XssUtil.escape(request.getParameter("uno").trim());
        String password = XssUtil.escape(request.getParameter("password").trim());
        String inputCaptcha = XssUtil.escape(request.getParameter("captcha")).trim();

        HttpSession session = request.getSession();
        String sessionCaptcha = (String) session.getAttribute("captchaCode");
        session.removeAttribute("captchaCode");

        // 1. 验证码错误：不计入锁定
        if (inputCaptcha == null || sessionCaptcha == null
                || !inputCaptcha.equalsIgnoreCase(sessionCaptcha)) {
        	String msg = URLEncoder.encode("验证码错误！", "UTF-8");
            response.sendRedirect("login.html?Msg=" + msg);
            return;
        }

        // 2. 先检查账号是否锁定
        String lockMsg = userService.checkAccountLock(uno);
        if (lockMsg != null) {
        	String msg = URLEncoder.encode(lockMsg, "UTF-8");
            response.sendRedirect("login.html?Msg=" + msg);
            return;
        }

        // 3.看看用户是否存在
        Users user = userService.getUserByUno(uno);
        if(user == null){
            String msg = URLEncoder.encode("该用户不存在！", "UTF-8");
            response.sendRedirect("login.html?Msg=" + msg);
            return;
        }

        // 4. 校验账号密码
        user = userService.login(uno, password);
        if (user == null) {
            // 账号密码错误：触发计数和锁定
            userService.handleLoginFail(uno);
            String msg=URLEncoder.encode("账号或密码错误！", "UTF-8");;
            int errorCount = usersDao.getUserByUno(uno).getErrorCount();
            if(errorCount>=11)
            	msg=URLEncoder.encode("账号或密码错误！账号已永久锁定，请联系管理员解锁", "UTF-8");
            else if(errorCount>=5)
            	msg=URLEncoder.encode("账号或密码错误！账号已锁定，"
            			+(int)Math.pow(2, errorCount-5)+"分钟后解锁", "UTF-8");
            else if(errorCount>=2)
            	msg=URLEncoder.encode("账号或密码错误！你还有"+(5-errorCount)+"次机会"
            			, "UTF-8");;
            response.sendRedirect("login.html?Msg="+msg);
            return;
        }

        // 5. 登录成功：清空错误计数和锁定状态
        userService.handleLoginSuccess(uno);
        session.removeAttribute("captchaCode");
        session.setAttribute("loginUser", user);
        System.out.println("登录成功，用户信息：" + user + "，Session ID：" + session.getId());
        // 这里用request.getContextPath()获取项目根路径，确保跳转正确
        response.sendRedirect(request.getContextPath() + "/pages/student.html");
    }
}