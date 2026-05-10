package com.sss.servlet;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sss.util.CaptchaUtil;

/**
 * 生成验证码图片的Servlet
 * 前端 src="/api/captcha" 访问此接口
 */
public class CaptchaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1. 设置响应头：输出图片，不缓存
        response.setContentType("image/jpeg");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        // 2. 生成验证码图片和文本
        Object[] captcha = CaptchaUtil.generateCaptcha();
        BufferedImage image = (BufferedImage) captcha[0];
        String code = (String) captcha[1];

        // 3. 把验证码文本存入Session（用于登录时校验）
        HttpSession session = request.getSession();
        session.setAttribute("captchaCode", code);

        // 4. 输出图片到前端
        ImageIO.write(image, "JPEG", response.getOutputStream());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}