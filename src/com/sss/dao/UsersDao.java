package com.sss.dao;

import com.sss.entity.Users;

public interface UsersDao {

    /**
     * 登录验证
     * @param uno 账号（学号/工号）
     * @param password 密码
     * @return 查到返回Users对象，没查到返回null
     */
 // 仅根据账号查询用户（不校验密码，用于锁定状态检查）
    public Users getUserByUno(String uno);

    // 更新连续错误次数
    public void updateErrorCount(String uno, int count);

    // 设置账号锁定时间和时长
    public void lockAccount(String uno, int lockMin);

    // 永久锁定
    public void permanentLock(String uno);

    // 登录成功：清空错误次数、锁定状态
    public void resetLockInfo(String uno);
}