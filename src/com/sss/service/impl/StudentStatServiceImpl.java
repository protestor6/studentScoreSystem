//made by “∂”¿ ¢
package com.sss.service.impl;

import com.sss.dao.impl.StudentStatDaoImpl;
import com.sss.entity.StudentStat;
import com.sss.service.StudentStatService;

public class StudentStatServiceImpl implements StudentStatService {

    @Override
    public StudentStat getStudentStat(String sno) {
        return new StudentStatDaoImpl().getStudentStat(sno);
    }
}
