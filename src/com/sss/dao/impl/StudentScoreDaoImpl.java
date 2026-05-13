package com.sss.dao.impl;

import com.sss.dao.StudentScoreDao;
import com.sss.entity.StudentScoreDetail;
import com.sss.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StudentScoreDaoImpl implements StudentScoreDao {
    @Override
    public List<StudentScoreDetail> queryScores(String uno, String term, String courseName, String courseCode, String sort) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<StudentScoreDetail> list = new ArrayList<StudentScoreDetail>();

        try {
            conn = DBUtil.getConnection();
            // 你的视图名为 v_student_score_detail
            StringBuilder sql = new StringBuilder("SELECT * FROM " +
            		"v_student_score_detail WHERE 学号 = ?");

            // 拼接筛选条件
            if (term != null && !term.trim().isEmpty()) {
                sql.append(" AND 开课学期 = ?");
            }
            if (courseName != null && !courseName.trim().isEmpty()) {
                sql.append(" AND 课程名称 LIKE ?");
            }
            if (courseCode != null && !courseCode.trim().isEmpty()) {
                sql.append(" AND 课程编号 LIKE ?");
            }

            // 拼接排序条件（关键：升序/降序）
            if ("desc".equals(sort)) {
                sql.append(" ORDER BY 成绩 DESC");
            } else if ("asc".equals(sort)) {
                sql.append(" ORDER BY 成绩 ASC");
            } else {
                // 默认按学期+课程号排序
                sql.append(" ORDER BY 成绩 DESC, 课程编号");
            }

            pstmt = conn.prepareStatement(sql.toString());

            // 设置参数
            int index = 1;
            pstmt.setString(index++, uno);
            if (term != null && !term.trim().isEmpty()) {
                pstmt.setString(index++, term);
            }
            if (courseName != null && !courseName.trim().isEmpty()) {
                pstmt.setString(index++, "%" + courseName + "%");
            }
            if (courseCode != null && !courseCode.trim().isEmpty()) {
                pstmt.setString(index++, "%" + courseCode + "%");
            }

            rs = pstmt.executeQuery();
            while (rs.next()) {
                StudentScoreDetail s = new StudentScoreDetail();
                s.setSno(rs.getString("学号"));
                s.setSname(rs.getString("学生姓名"));
                s.setSemester(rs.getString("开课学期"));
                s.setCno(rs.getString("课程编号"));
                s.setCname(rs.getString("课程名称"));
                s.setScore(rs.getString("成绩"));
                s.setCredit(rs.getDouble("学分"));
                s.setPeriod(rs.getInt("总学时"));
                s.setGpa(rs.getDouble("绩点"));
                s.setTestmethod(rs.getString("考核方式"));
                s.setCtype(rs.getString("课程类型"));
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, pstmt, conn);
        }
        return list;
    }
}