package com.weiyun.peoplecounting.service;

import com.weiyun.peoplecounting.error.BusinessException;
import com.weiyun.peoplecounting.pojo.Teacher;
import com.weiyun.peoplecounting.response.CommonReturnType;

public interface TeacherInfoService {

    CommonReturnType addTeacherInfo(Teacher teacher, Integer userId) throws BusinessException;

}
