package com.sss.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.sss.dao.UsersDao;
import com.sss.entity.Users;
import com.sss.entity.UserType;
import com.sss.util.DBUtil;

/**
 * 用户登录数据访问层
 * 作用：根据账号密码查询用户
 */
public class UsersDaoImpl implements UsersDao {

    /**
     * 登录验证
     * @param uno 账号（学号/工号）
     * @param password 密码
     * @return 查到返回Users对象，没查到返回null
     */
    public Users login(String uno, String password) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Users user = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM users WHERE uno=? AND password=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, uno);
            pstmt.setString(2, password);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                user = new Users();
                user.setId(rs.getInt("id"));
                user.setUtype(UserType.valueOf(rs.getString("utype")));
                user.setUno(rs.getString("uno"));
                user.setPassword(rs.getString("password"));
                user.setAdmno(rs.getInt("admno"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, pstmt, conn);
        }

        return user;
    }
}