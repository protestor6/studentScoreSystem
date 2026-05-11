package com.sss.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 数据库连接工具类（适配MySQL utf8mb4 + JDK1.6 + MyEclipse8.6）
 * 兼容SSSdatabase的utf8mb4编码
 */
public class DBUtil {
    // 数据库连接参数（改成你自己的MySQL账号密码）
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    // 适配utf8mb4的URL（核心修改）
    private static final String URL = "jdbc:mysql://localhost:3306/SSSdatabase?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8";
    private static final String USER = "user";  // 你的MySQL用户名（默认root）
    private static final String PASSWORD = "user";  // 你的MySQL密码，改成自己的

    // 静态代码块：加载驱动（只执行一次）
    static {
        try {
            Class.forName(DRIVER);
            System.out.println("MySQL驱动加载成功！");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("MySQL驱动加载失败！请检查jar包是否导入");
        }
    }

    /**
     * 获取数据库连接
     * @return Connection 连接对象
     */
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("成功连接到SSSdatabase数据库！");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("数据库连接失败！请检查账号密码/数据库名/编码配置");
        }
        return conn;
    }

    /**
     * 关闭数据库资源（ResultSet + PreparedStatement + Connection）
     * @param rs 结果集
     * @param pstmt 预编译语句
     * @param conn 连接对象
     */
    public static void close(ResultSet rs, PreparedStatement pstmt, Connection conn) {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 重载：关闭资源（无ResultSet时用）
     */
    public static void close(PreparedStatement pstmt, Connection conn) {
        close(null, pstmt, conn);
    }

    // 测试方法：运行后看控制台是否输出连接成功
    public static void main(String[] args) {
        getConnection();
    }
}