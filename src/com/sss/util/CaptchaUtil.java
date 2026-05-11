//made by 叶永盛
package com.sss.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;

/**
 * 验证码生成工具类
 * 生成4位包含大小写字母+数字的验证码图片
 */
public class CaptchaUtil {
    // 验证码字符集：大小写字母+数字
    private static final String CODES = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz23456789";
    private static final int CODE_LENGTH = 4; // 4位验证码
    private static final int WIDTH = 120;     // 图片宽度
    private static final int HEIGHT = 40;     // 图片高度

    /**
     * 生成验证码图片和验证码文本
     * @return 数组：[0] = 图片对象, [1] = 验证码文本
     */
    public static Object[] generateCaptcha() {
        // 1. 创建图片对象
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        SecureRandom random = new SecureRandom();

        // 2. 设置背景
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // 3. 画干扰线
        g.setColor(Color.GRAY);
        for (int i = 0; i < 10; i++) {
            int x1 = random.nextInt(WIDTH);
            int y1 = random.nextInt(HEIGHT);
            int x2 = random.nextInt(WIDTH);
            int y2 = random.nextInt(HEIGHT);
            g.drawLine(x1, y1, x2, y2);
        }

        // 4. 生成验证码文本
        StringBuilder code = new StringBuilder();
        g.setFont(new Font("Arial", Font.BOLD, 24));
        for (int i = 0; i < CODE_LENGTH; i++) {
            // 随机取一个字符
            char c = CODES.charAt(random.nextInt(CODES.length()));
            code.append(c);
            // 随机颜色
            g.setColor(new Color(random.nextInt(200), random.nextInt(200), random.nextInt(200)));
            // 画字符
            g.drawString(String.valueOf(c), 20 + i * 25, 28);
        }

        // 5. 释放资源
        g.dispose();

        return new Object[]{image, code.toString()};
    }
}