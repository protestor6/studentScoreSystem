//made by “∂”¿ ¢
package com.sss.dao.impl;

import com.sss.dao.StudentDao;
import com.sss.entity.Student;
import com.sss.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StudentDaoImpl implements StudentDao {

    @Override
    public Student getStudentBySno(String sno) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Student student = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT sno, sname, sex, enroll_time, sid, classno FROM student WHERE sno = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, sno);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                student = new Student();
                student.setSno(rs.getString("sno"));
                student.setSname(rs.getString("sname"));
                student.setSex(rs.getString("sex"));
                student.setEnrollTime(rs.getDate("enroll_time"));
                student.setSid(rs.getString("sid"));
                student.setClassno(rs.getInt("classno"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	DBUtil.close(rs, pstmt, conn);
        }
        return student;
    }
}