package com.sss.entity;

public class StudentScoreDetail {
    private String sno;        // 学号
    private String sname;      // 学生姓名
    private String semester;   // 开课学期
    private String cno;        // 课程编号
    private String cname;      // 课程名称
    private String score;      // 成绩（关键：用String，兼容数字和等级）
    private Double credit;     // 学分
    private Integer period;    // 总学时
    private Double gpa;        // 绩点
    private String testmethod; // 考核方式
    private String ctype;      // 课程类型

    // 无参构造器
    public StudentScoreDetail() {}

    // 全参构造器（可选）
    public StudentScoreDetail(String sno, String sname, String semester, String cno, String cname,
                               String score, Double credit, Integer period, Double gpa,
                               String testmethod, String ctype) {
        this.sno = sno;
        this.sname = sname;
        this.semester = semester;
        this.cno = cno;
        this.cname = cname;
        this.score = score;
        this.credit = credit;
        this.period = period;
        this.gpa = gpa;
        this.testmethod = testmethod;
        this.ctype = ctype;
    }

    // Getter 和 Setter
    public String getSno() { return sno; }
    public void setSno(String sno) { this.sno = sno; }

    public String getSname() { return sname; }
    public void setSname(String sname) { this.sname = sname; }

    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }

    public String getCno() { return cno; }
    public void setCno(String cno) { this.cno = cno; }

    public String getCname() { return cname; }
    public void setCname(String cname) { this.cname = cname; }

    public String getScore() { return score; }
    public void setScore(String score) { this.score = score; }

    public Double getCredit() { return credit; }
    public void setCredit(Double credit) { this.credit = credit; }

    public Integer getPeriod() { return period; }
    public void setPeriod(Integer period) { this.period = period; }

    public Double getGpa() { return gpa; }
    public void setGpa(Double gpa) { this.gpa = gpa; }

    public String getTestmethod() { return testmethod; }
    public void setTestmethod(String testmethod) { this.testmethod = testmethod; }

    public String getCtype() { return ctype; }
    public void setCtype(String ctype) { this.ctype = ctype; }

    @Override
    public String toString() {
        return "StudentScoreDetail{" +
                "sno='" + sno + '\'' +
                ", sname='" + sname + '\'' +
                ", semester='" + semester + '\'' +
                ", cno='" + cno + '\'' +
                ", cname='" + cname + '\'' +
                ", score='" + score + '\'' +
                ", credit=" + credit +
                ", period=" + period +
                ", gpa=" + gpa +
                ", testmethod='" + testmethod + '\'' +
                ", ctype='" + ctype + '\'' +
                '}';
    }
}