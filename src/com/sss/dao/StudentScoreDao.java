//made by 叶永盛
package com.sss.dao;

import com.sss.entity.StudentScoreDetail;
import java.util.List;

public interface StudentScoreDao {
	//查询成绩信息
    List<StudentScoreDetail> queryScores(String uno
    		, String term, String courseName, String courseCode, String sort);
}