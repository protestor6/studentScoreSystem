//made by 叶永盛
package com.sss.service;

import com.sss.entity.Users;

public interface UsersService {
    // 看看用户是否存在
    public Users getUserByUno(String uno);
    // 登录
	public Users login(String uno, String password);
	// 检查账号是否被锁定：返回null=正常，返回提示文案=已锁定
    public String checkAccountLock(String uno);

    // 账号密码错误：处理计数+锁定规则
    public void handleLoginFail(String uno);

    // 登录成功：清空锁定和计数
    public void handleLoginSuccess(String uno);
    
    //通过uno获取用户姓名
    public String getUname(String uno);
}
