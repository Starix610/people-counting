package com.weiyun.peoplecounting.service.impl;

import com.weiyun.peoplecounting.error.BusinessException;
import com.weiyun.peoplecounting.error.EmBusinessError;
import com.weiyun.peoplecounting.mapper.TeacherMapper;
import com.weiyun.peoplecounting.mapper.UserMapper;
import com.weiyun.peoplecounting.pojo.Teacher;
import com.weiyun.peoplecounting.pojo.User;
import com.weiyun.peoplecounting.response.CommonReturnType;
import com.weiyun.peoplecounting.service.TeacherInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class TeacherInfoServiceImpl implements TeacherInfoService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private UserMapper userMapper;


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public CommonReturnType addTeacherInfo(Teacher teacher, Integer userId) throws BusinessException {

        User user = userMapper.selectByTeacherId(teacher.getId());
        if (user != null && !user.getId().equals(userId)){   //教师信息已经被另一用户绑定
            throw new BusinessException(EmBusinessError.TEACHER_INFO_ALREADY_ADDED);
        }

        //教师表教师表是否已经存在该教师的信息,不存在则插入存在则更新
        Teacher teacher1 = teacherMapper.selectByPrimaryKey(teacher.getId());
        if (teacher1 == null){
            teacherMapper.insert(teacher);
        }else {
            teacherMapper.updateByPrimaryKey(teacher);
        }

        //更新用户绑定信息
        userMapper.updateTeacherInfoByUserId(teacher.getId(),userId);

        return CommonReturnType.create("教师认证信息添加成功");
    }

}
