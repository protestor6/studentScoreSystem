//made by 叶永盛
package com.sss.dao;

import com.sss.entity.Student;

public interface StudentDao {
    // 根据学号（sno）查询学生信息
    Student getStudentBySno(String sno);
}