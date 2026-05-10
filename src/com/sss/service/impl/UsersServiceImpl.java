package com.sss.service.impl;

import com.sss.dao.UsersDao;
import com.sss.dao.impl.UsersDaoImpl;
import com.sss.entity.Users;
import com.sss.service.UsersService;

public class UsersServiceImpl implements UsersService {
	private UsersDao usersDao = new UsersDaoImpl();

    public Users login(String uno, String password) {
        return usersDao.login(uno, password);
    }
}
