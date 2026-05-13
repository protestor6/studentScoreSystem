//made by 叶永盛
package com.sss.service.impl;

import java.util.Date;
import org.mindrot.jbcrypt.BCrypt;
import com.sss.dao.UsersDao;
import com.sss.dao.impl.UsersDaoImpl;
import com.sss.entity.UserType;
import com.sss.entity.Users;
import com.sss.service.StudentService;
import com.sss.service.UsersService;

public class UsersServiceImpl implements UsersService {
    private UsersDao usersDao = new UsersDaoImpl();

    @Override
    public Users getUserByUno(String uno) {
        return usersDao.getUserByUno(uno);
    }

    public Users login(String uno, String password) {
        Users user = usersDao.getUserByUno(uno);

        // ========== 调试看是否取到用户和密码 ==========
        System.out.println("登录账号：" + uno);
        System.out.println("查询到用户：" + (user != null ? "存在" : "不存在"));
        if (user != null) {
            System.out.println("数据库密码：" + user.getPassword());
            System.out.println("用户输入密码：" + password);
        }

        if (user == null || user.getPassword() == null) {
            return null;
        }

        try {
            boolean ok = BCrypt.checkpw(password, user.getPassword());
            System.out.println("BCrypt 校验结果：" + ok);
            if (ok) {
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String checkAccountLock(String uno) {
        Users user = usersDao.getUserByUno(uno);
        if (user == null) return null;

        if (user.getIsPermanentLock() == 1) {
            return "账号已永久锁定，请联系管理员解锁";
        }

        Date lockTime = user.getLockTime();
        int lockMin = user.getLockMinutes();
        if (lockTime != null && lockMin > 0) {
            long now = System.currentTimeMillis();
            long unlockTime = lockTime.getTime() + (long) lockMin * 60 * 1000;
            if (now < unlockTime) {
                long remainMin = (long)Math.ceil(1.0*(unlockTime - now) / 1000 / 60);
                return "账号已锁定，剩余 " + remainMin + " 分钟后解锁";
            }
        }
        return null;
    }

    public void handleLoginFail(String uno) {
        Users user = usersDao.getUserByUno(uno);
        if (user == null) return;

        int newCount = user.getErrorCount() + 1;
        usersDao.updateErrorCount(uno, newCount);

        if (newCount >= 11) {
            usersDao.permanentLock(uno);
        } else if (newCount == 5) {
            usersDao.lockAccount(uno, 1);
        } else if (newCount > 5) {
            int newLockMin = user.getLockMinutes() * 2;
            usersDao.lockAccount(uno, newLockMin);
        }
    }

    public void handleLoginSuccess(String uno) {
        usersDao.resetLockInfo(uno);
    }

	@Override
	public String getUname(String uno) {
		Users user = usersDao.getUserByUno(uno);
		if (user == null) return null;
		
		if(user.getUtype()==UserType.学生){
			StudentService studentService = new StudentServiceImpl();
			return studentService.getSname(uno);
		}
		return null;
	}
}