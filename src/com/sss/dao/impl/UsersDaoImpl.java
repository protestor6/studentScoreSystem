package com.sss.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import com.sss.dao.UsersDao;
import com.sss.entity.UserType;
import com.sss.entity.Users;
import com.sss.util.DBUtil;

public class UsersDaoImpl implements UsersDao {

    // 根据账号查询用户（包含密码）
    public Users getUserByUno(String uno) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Users user = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM users WHERE uno=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, uno);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new Users();
                user.setUid(rs.getInt("uid"));
                user.setUtype(UserType.valueOf(rs.getString("utype")));
                user.setUno(rs.getString("uno"));
                user.setPassword(rs.getString("password")); // 这里必须取到！
                user.setAdmno(rs.getInt("admno"));
                user.setErrorCount(rs.getInt("error_count"));
                user.setLockTime(rs.getTimestamp("lock_time"));
                user.setLockMinutes(rs.getInt("lock_minutes"));
                user.setIsPermanentLock(rs.getInt("is_permanent_lock"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, pstmt, conn);
        }
        return user;
    }

    public void updateErrorCount(String uno, int count) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE users SET error_count=? WHERE uno=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, count);
            pstmt.setString(2, uno);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(pstmt, conn);
        }
    }

    public void lockAccount(String uno, int lockMin) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE users SET lock_time=?, lock_minutes=? WHERE uno=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            pstmt.setInt(2, lockMin);
            pstmt.setString(3, uno);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(pstmt, conn);
        }
    }

    public void permanentLock(String uno) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE users SET is_permanent_lock=1 WHERE uno=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, uno);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(pstmt, conn);
        }
    }

    public void resetLockInfo(String uno) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE users SET error_count=0, lock_time=NULL, lock_minutes=0, is_permanent_lock=0 WHERE uno=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, uno);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(pstmt, conn);
        }
    }
}