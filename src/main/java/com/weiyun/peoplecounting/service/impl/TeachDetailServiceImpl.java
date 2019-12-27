package com.weiyun.peoplecounting.service.impl;

import com.weiyun.peoplecounting.error.BusinessException;
import com.weiyun.peoplecounting.error.EmBusinessError;
import com.weiyun.peoplecounting.mapper.TeachDetailMapper;
import com.weiyun.peoplecounting.mapper.TeachMapper;
import com.weiyun.peoplecounting.pojo.Teach;
import com.weiyun.peoplecounting.pojo.TeachDetail;
import com.weiyun.peoplecounting.response.CommonReturnType;
import com.weiyun.peoplecounting.service.TeachDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class TeachDetailServiceImpl implements TeachDetailService {

    @Autowired
    private TeachDetailMapper teachDetailMapper;
    @Autowired
    private TeachMapper teachMapper;




    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public CommonReturnType addTeachAnfo(TeachDetail teachDetail, String teacherId) throws BusinessException {

        //授课表中是否添加过该教师的该门课程
        Teach teach = teachMapper.selectByCourseIdAndTeacherId(teachDetail.getCourseId(),teacherId);
        if (teach == null){
            teach = new Teach();
            teach.setCourseId(teachDetail.getCourseId());
            teach.setTeacherId(teacherId);
            teachMapper.insert(teach);
        }
        //授课详情表是否已经添加过相同时段的相同课程
        int isExist = teachDetailMapper.selectToCheckExist(teacherId,teachDetail.getCourseId(),teachDetail.getWeekday(),teachDetail.getSequence());
        if (isExist == 1){
            throw new BusinessException(EmBusinessError.COURSE_INFO_ALREADY_EXIST);
        }
        teachDetail.setId(UUID.randomUUID().toString());
        teachDetail.setTeacherId(teacherId);
        teachDetailMapper.insert(teachDetail);
        return CommonReturnType.create("课程信息添加成功");
    }




}
