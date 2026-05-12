package com.sss.entity;

import java.util.Date;

public class Student {
    // 对应数据库字段：sno, sname, sex, enroll_time, sid, classno
    private String sno;         // 学号（主键）
    private String sname;       // 姓名
    private String sex;         // 性别
    private Date enrollTime;    // 入学时间（数据库字段 enroll_time）
    private String sid;         // 身份证号/学生ID
    private Integer classno;    // 班级号

    // 无参构造器
    public Student() {
    }

    // Getter 和 Setter
    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getEnrollTime() {
        return enrollTime;
    }

    public void setEnrollTime(Date enrollTime) {
        this.enrollTime = enrollTime;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public Integer getClassno() {
        return classno;
    }

    public void setClassno(Integer classno) {
        this.classno = classno;
    }
}