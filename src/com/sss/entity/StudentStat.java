//made by 叶永盛
package com.sss.entity;
import java.util.List;

public class StudentStat {
    private double totalGpa;       // 总绩点
    private double avgGpa;         // 平均绩点
    private double completedCredits; // 已修学分
    private double uncompletedCredits; // 未修学分
    private String studyProgress;  // 学业进度
    private List<StudentScoreDetail> courseList; // 课程列表

    // getter
    public double getTotalGpa() { return totalGpa; }
    public double getAvgGpa() { return avgGpa; }
    public double getCompletedCredits() { return completedCredits; }
    public double getUncompletedCredits() { return uncompletedCredits; }
    public String getStudyProgress() { return studyProgress; }
    public List<StudentScoreDetail> getCourseList() { return courseList; }

    // setter
    public void setTotalGpa(double totalGpa) { this.totalGpa = totalGpa; }
    public void setAvgGpa(double avgGpa) { this.avgGpa = avgGpa; }
    public void setCompletedCredits(double completedCredits) { this.completedCredits = completedCredits; }
    public void setUncompletedCredits(double uncompletedCredits) { this.uncompletedCredits = uncompletedCredits; }
    public void setStudyProgress(String studyProgress) { this.studyProgress = studyProgress; }
    public void setCourseList(List<StudentScoreDetail> courseList) { this.courseList = courseList; }
}