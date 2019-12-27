package com.weiyun.peoplecounting.service.impl;

import com.weiyun.peoplecounting.error.BusinessException;
import com.weiyun.peoplecounting.mapper.CollegeMapper;
import com.weiyun.peoplecounting.mapper.CourseMapper;
import com.weiyun.peoplecounting.mapper.TeachDetailMapper;
import com.weiyun.peoplecounting.pojo.College;
import com.weiyun.peoplecounting.pojo.Course;
import com.weiyun.peoplecounting.pojo.TeachDetailVO;
import com.weiyun.peoplecounting.service.ComboxDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class ComboxDataServiceImpl implements ComboxDataService {

    
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private CollegeMapper collegeMapper;
    @Autowired
    private TeachDetailMapper teachDetailMapper;


    //获得所有课程信息
    @Override
    public List<Course> getCourses() {
        List<Course> courseList = courseMapper.selectAll();
        return courseList;
    }


    //获取学院信息
    @Override
    public List<College> getColleges() {
        List<College> collegeList = collegeMapper.selectAll();
        return collegeList;
    }

    //获得自己绑定过的课程授课信息
    @Override
    public List<TeachDetailVO> getCoursesByTeacherId(String teacherId) throws BusinessException {
        List<TeachDetailVO> teachDetailList = teachDetailMapper.selectTeachDetailVo(teacherId);
        return teachDetailList;
    }


}
