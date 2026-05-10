package com.sss.dao;

import com.sss.entity.Users;

public interface UsersDao {

    /**
     * 登录验证
     * @param uno 账号（学号/工号）
     * @param password 密码
     * @return 查到返回Users对象，没查到返回null
     */
    public Users login(String uno, String password);
}