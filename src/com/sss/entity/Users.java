//made by 叶永盛
package com.sss.entity;

import java.util.Date;
/**
 * 用户实体类
 * 已集成：登录错误计数 + 锁定字段，不用额外建表
 */
public class Users {
    private int uid;
    private UserType utype;
    private String uno;
    private String password;
    private Integer admno;

    // 登录锁定相关字段
    private int errorCount;       // 连续错误次数
    private Date lockTime;        // 锁定开始时间
    private int lockMinutes;     // 锁定分钟
    private int isPermanentLock; // 1永久锁定 0正常

    // 无参构造
    public Users() {}

    // get/set
    public int getUid() { return uid; }
    public void setUid(int uid) { this.uid = uid; }

    public UserType getUtype() { return utype; }
    public void setUtype(UserType utype) { this.utype = utype; }

    public String getUno() { return uno; }
    public void setUno(String uno) { this.uno = uno; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Integer getAdmno() { return admno; }
    public void setAdmno(Integer admno) { this.admno = admno; }

    // 新增：锁定相关 get/set
    public int getErrorCount() { return errorCount; }
    public void setErrorCount(int errorCount) { this.errorCount = errorCount; }

    public Date getLockTime() { return lockTime; }
    public void setLockTime(Date lockTime) { this.lockTime = lockTime; }

    public int getLockMinutes() { return lockMinutes; }
    public void setLockMinutes(int lockMinutes) { this.lockMinutes = lockMinutes; }

    public int getIsPermanentLock() { return isPermanentLock; }
    public void setIsPermanentLock(int isPermanentLock) { this.isPermanentLock = isPermanentLock; }
}