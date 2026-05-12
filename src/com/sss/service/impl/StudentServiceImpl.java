package com.sss.service.impl;

import com.sss.dao.StudentDao;
import com.sss.dao.impl.StudentDaoImpl;
import com.sss.entity.Student;
import com.sss.service.StudentService;

public class StudentServiceImpl implements StudentService {

	@Override
	public String getSname(String sno) {
		StudentDao studentDao=new StudentDaoImpl();
		Student student=studentDao.getStudentBySno(sno);
		if(student==null)	return null;
		return student.getSname();
	}

}
