package com.weiyun.peoplecounting.mapper;

import com.weiyun.peoplecounting.pojo.Teacher;

import java.util.List;

public interface TeacherMapper {

    int deleteByPrimaryKey(String id);

    int insert(Teacher record);

    Teacher selectByPrimaryKey(String id);

    List<Teacher> selectAll();

    int updateByPrimaryKey(Teacher record);

}