package com.weiyun.peoplecounting.mapper;

import com.weiyun.peoplecounting.pojo.Course;
import com.weiyun.peoplecounting.pojo.Teach;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeachMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Teach record);

    Teach selectByPrimaryKey(Integer id);

    List<Teach> selectAll();

    int updateByPrimaryKey(Teach record);

    Teach selectByCourseIdAndTeacherId(@Param("courseId") String courseId, @Param("teacherId") String teacherId);

    List<Course> selectForCourseByTeacherId(@Param("teacherId")String teacherId);
}