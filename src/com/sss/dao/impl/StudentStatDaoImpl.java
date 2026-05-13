//made by 叶永盛
package com.sss.dao.impl;

import com.sss.dao.StudentStatDao;
import com.sss.entity.StudentScoreDetail;
import com.sss.entity.StudentStat;
import com.sss.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StudentStatDaoImpl implements StudentStatDao {
    @Override
    public StudentStat getStudentStat(String sno) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StudentStat stat = new StudentStat();
        List<StudentScoreDetail> courseList = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            //测试：确认传入的学号
            System.out.println("查询统计数据，学号：" + sno);

            // 1. 统计绩点、学分
            String sql = "SELECT * FROM v_student_stat_detail WHERE 学号 = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, sno);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                // 注意：这里列名要和你视图里的中文别名完全一样！
                stat.setTotalGpa(rs.getDouble("个人总绩点"));
                stat.setAvgGpa(rs.getDouble("平均学分绩点"));
                stat.setCompletedCredits(rs.getDouble("已修学分"));
                stat.setUncompletedCredits(rs.getDouble("未修学分"));

                // 总学分也读出来，用来算进度
                double totalAllCredits = rs.getDouble("总学分");
                double completed = stat.getCompletedCredits();
                // 生成学业进度字符串
                String progress = String.format("%.1f/%.1f (%.0f%%)",
                        completed,
                        totalAllCredits,
                        (completed / totalAllCredits) * 100);
                stat.setStudyProgress(progress);
            }

            // 2. 获取所有课程列表
            String courseSql = "SELECT * FROM v_student_score_detail WHERE 学号 = ? ORDER BY 开课学期 DESC";
            pstmt = conn.prepareStatement(courseSql);
            pstmt.setString(1, sno);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                StudentScoreDetail course = new StudentScoreDetail();
                course.setCno(rs.getString("课程编号"));
                course.setCname(rs.getString("课程名称"));
                course.setCredit(rs.getDouble("学分"));
                course.setScore(rs.getString("成绩"));
                course.setGpa(rs.getDouble("绩点"));
                course.setSemester(rs.getString("开课学期"));
                courseList.add(course);
            }
            stat.setCourseList(courseList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, pstmt, conn);
        }
        return stat;
    }
}
