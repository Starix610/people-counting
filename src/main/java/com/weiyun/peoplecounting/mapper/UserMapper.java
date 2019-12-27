package com.weiyun.peoplecounting.mapper;

import com.weiyun.peoplecounting.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {


    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    User selectByPrimaryKey(Integer id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

    User selectByTelephone(@Param("telephone") String telephone);

    void updateTeacherInfoByUserId(@Param("teacherId") String teacherId, @Param("userId") Integer id);

    User selectByTeacherId(@Param("teacherId") String teacherId);
}