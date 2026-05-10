package com.sss.entity;

/**
 * 用户实体类（对应数据库 users 表）
 * 用于登录功能
 */

public class Users {
    private int id;          // 主键id
    private UserType utype;    // 用户类型：学生/教师/管理员
    private String uno;      // 账号（学号/工号）
    private String password; // 密码
    private Integer admno;    // 管理员编号（可为null）

    // 无参构造
    public Users() {}

	// get & set
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserType getUtype() {
        return utype;
    }

    public void setUtype(UserType utype) {
        this.utype = utype;
    }

    public String getUno() {
        return uno;
    }

    public void setUno(String uno) {
        this.uno = uno;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAdmno() {
        return admno;
    }

    public void setAdmno(Integer admno) {
        this.admno = admno;
    }
}