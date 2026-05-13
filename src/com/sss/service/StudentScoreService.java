//made by “∂”¿ ¢
package com.sss.service;

import com.sss.entity.StudentScoreDetail;
import java.util.List;

public interface StudentScoreService {
	List<StudentScoreDetail> queryScores(String uno, String term, String courseName
			, String courseCode, String sort);
}
