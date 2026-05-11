//made by 叶永盛
package com.sss.servlet;

import com.sss.dao.UsersDao;
import com.sss.dao.impl.UsersDaoImpl;
import com.sss.entity.Users;
import com.sss.service.UsersService;
import com.sss.service.impl.UsersServiceImpl;
import com.sss.util.BCryptUtil;
import com.sss.util.XssUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;

public class ChangePwdServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UsersDao usersDao = new UsersDaoImpl();
    private UsersService userService = new UsersServiceImpl();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        response.setHeader("X-XSS-Protection", "1;mode=block");//防XSS脚本攻击
        response.setHeader("X-Content-Type-Options", "nosniff");//防MIME嗅探
        response.setHeader("X-Frame-Options", "DENY");//防iframe嵌套
        response.setHeader("Content-Security-Policy", "default-src 'self'");//防非法脚本执行
        // XSS过滤参数
        String uno = XssUtil.escape(request.getParameter("uno")).trim();
        String oldPwd = XssUtil.escape(request.getParameter("oldPwd")).trim();
        String newPwd = XssUtil.escape(request.getParameter("newPwd")).trim();
        String confirmPwd = XssUtil.escape(request.getParameter("confirmPwd")).trim();

        String tipMsg;

        String ctx = request.getContextPath();
        // 1. 校验两次新密码是否一致
        if (!newPwd.equals(confirmPwd)) {
            tipMsg = URLEncoder.encode("两次输入的新密码不一致！", "UTF-8");
            response.sendRedirect(ctx+"/pages/changePwd.html?msg=" + tipMsg);
            return;
        }

        // 2. 校验账号是否存在
        Users user = usersDao.getUserByUno(uno);
        if (user == null) {
            tipMsg = URLEncoder.encode("账号不存在！", "UTF-8");
            response.sendRedirect(ctx+"/pages/changePwd.html?msg=" + tipMsg);
            return;
        }

        // 3. 校验账号是否被锁定
        if (userService.checkAccountLock(uno)!=null) {
            tipMsg = URLEncoder.encode("已锁定的账号不能改密码！", "UTF-8");
            response.sendRedirect(ctx+"/pages/changePwd.html?msg=" + tipMsg);
            return;
        }
        
        // 4. 校验原密码是否正确
        if (userService.login(uno, oldPwd)==null) {
            tipMsg = URLEncoder.encode("原密码错误！", "UTF-8");
            response.sendRedirect(ctx+"/pages/changePwd.html?msg=" + tipMsg);
            return;
        }

        // 5. 校验新密码长度（可按需求修改）
        if (newPwd.length() < 6) {
            tipMsg = URLEncoder.encode("新密码长度不能少于6位！", "UTF-8");
            response.sendRedirect(ctx+"/pages/changePwd.html?msg=" + tipMsg);
            return;
        }

        // 6. 更新数据库密码
        user.setPassword(BCryptUtil.encrypt(newPwd));
        boolean updateSuccess = usersDao.updatePassword(user);

        if (updateSuccess) {
        	HttpSession session = request.getSession();
            if (session != null) {
                session.invalidate(); // 销毁当前登录态
            }
            tipMsg = URLEncoder.encode("密码修改成功，请重新登录！", "UTF-8");
            response.sendRedirect(ctx+"/login.html?Msg=" + tipMsg);
        } else {
            tipMsg = URLEncoder.encode("密码修改失败，请稍后重试！", "UTF-8");
            response.sendRedirect(ctx+"/pages/changePwd.html?msg=" + tipMsg);
        }
    }
}