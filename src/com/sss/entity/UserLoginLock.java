package com.sss.entity;

import java.util.Date;

public class UserLoginLock {
    private int id;
    private String uno;
    private int errorCount;
    private Date lockTime;
    private int lockMinutes;
    private int isPermanentLock;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getUno() { return uno; }
    public void setUno(String uno) { this.uno = uno; }
    public int getErrorCount() { return errorCount; }
    public void setErrorCount(int errorCount) { this.errorCount = errorCount; }
    public Date getLockTime() { return lockTime; }
    public void setLockTime(Date lockTime) { this.lockTime = lockTime; }
    public int getLockMinutes() { return lockMinutes; }
    public void setLockMinutes(int lockMinutes) { this.lockMinutes = lockMinutes; }
    public int getIsPermanentLock() { return isPermanentLock; }
    public void setIsPermanentLock(int isPermanentLock) { this.isPermanentLock = isPermanentLock; }
}