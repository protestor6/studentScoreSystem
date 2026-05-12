package com.sss.service.impl;

import com.sss.dao.StudentScoreDao;
import com.sss.dao.impl.StudentScoreDaoImpl;
import com.sss.entity.StudentScoreDetail;
import com.sss.service.StudentScoreService;

import java.util.List;

public class StudentScoreServiceImpl implements StudentScoreService {
    private StudentScoreDao StudentScoreDao = new StudentScoreDaoImpl();

    @Override
    public List<StudentScoreDetail> queryScores(String uno, String term, String courseName, String courseCode, String sort) {
        return StudentScoreDao.queryScores(uno, term, courseName, courseCode, sort);
    }
}