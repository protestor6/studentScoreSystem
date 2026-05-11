package com.sss.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * BCrypt 密码加密工具类
 * 作用：输入明文密码 → 输出 BCrypt 密文（存入数据库）
 */
public class BCryptUtil {

    /**
     * 加密明文密码
     * @param plainPassword 明文密码（如：123456、Yys77337）
     * @return BCrypt 加密后的密文
     */
    public static String encrypt(String plainPassword) {
        // 自动生成随机盐 + 加密
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    /**
     * 校验明文与密文是否匹配
     */
    public static boolean check(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

    // 运行这个 main 方法就能生成密文
    public static void main(String[] args) {
        // ======================
        // 在这里改你要加密的明文
        // ======================
        String plain = "t0000004";

        // 生成密文
        String hashed = encrypt(plain);
        System.out.println("明文：" + plain);
        System.out.println("BCrypt 密文：" + hashed);

        // 测试校验是否正确
        System.out.println("校验是否正确：" + check(plain, hashed));
    }
}