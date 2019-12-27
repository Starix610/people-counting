package com.weiyun.peoplecounting.service;

import com.weiyun.peoplecounting.error.BusinessException;
import com.weiyun.peoplecounting.pojo.College;
import com.weiyun.peoplecounting.pojo.Course;
import com.weiyun.peoplecounting.pojo.TeachDetailVO;

import java.util.List;

public interface ComboxDataService {

    List<Course> getCourses();

    List<TeachDetailVO> getCoursesByTeacherId(String teacherId) throws BusinessException;

    List<College> getColleges();
}
