package com.weiyun.peoplecounting.service.impl;

import com.weiyun.peoplecounting.error.BusinessException;
import com.weiyun.peoplecounting.error.EmBusinessError;
import com.weiyun.peoplecounting.mapper.AttendanceMapper;
import com.weiyun.peoplecounting.mapper.TeachDetailMapper;
import com.weiyun.peoplecounting.pojo.Attendance;
import com.weiyun.peoplecounting.pojo.TeachDetail;
import com.weiyun.peoplecounting.response.CommonReturnType;
import com.weiyun.peoplecounting.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class DataServiceImpl implements DataService {

    @Autowired
    private AttendanceMapper attendanceMapper;
    @Autowired
    private TeachDetailMapper teachDetailMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public CommonReturnType saveData(String teachDetailId, Integer count, String orginalImg, String detectionImg) throws BusinessException {

        TeachDetail teachDetail = teachDetailMapper.selectByPrimaryKey(teachDetailId);
        if (teachDetail == null) {
            throw new BusinessException(EmBusinessError.NO_COURSE_ADDED);
        }
        if (count>teachDetail.getTotalNumber()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION,"数据异常，上传人数超过了该课程总人数");
        }

        //数据库存在当天该节课识别数据则更新，不存在则插入数据，保证同一节课只有一个数据
        Attendance attendance = attendanceMapper.selectToday(teachDetail.getId());
        if (attendance != null){
            attendance.setAttendanceNum(count);
            attendance.setAttendanceRate(count.doubleValue()/teachDetail.getTotalNumber().doubleValue());
            attendance.setRecordTime(new Date());
            attendance.setOriginalImage(orginalImg);
            attendance.setDetectionImage(detectionImg);
            int update = attendanceMapper.updateAttendance(attendance);
            if (update == 0){
                throw new BusinessException(EmBusinessError.UNKNOWN_ERROR,"数据保存失败,请稍后重试");
            }
        }else {
            attendance = new Attendance();
            //TeachDetail teachDetail1 = teachDetailMapper.selectTeachDetailId(teachDetail.getCourseId(),teacherId,teachDetail.getWeekday(),teachDetail.getSequence());
            attendance.setId(UUID.randomUUID().toString());
            attendance.setTeachDetailId(teachDetail.getId()); //授课详情表外键
            attendance.setTotalNumber(teachDetail.getTotalNumber());
            attendance.setAttendanceNum(count);
            attendance.setAttendanceRate(count.doubleValue()/teachDetail.getTotalNumber().doubleValue());
            attendance.setClassroom(teachDetail.getClassroom());
            attendance.setRecordTime(new Date());
            attendance.setOriginalImage(orginalImg);
            attendance.setDetectionImage(detectionImg);
            int insert = attendanceMapper.insert(attendance);
            if (insert == 0){
                throw new BusinessException(EmBusinessError.UNKNOWN_ERROR,"数据保存失败,请稍后重试");
            }
        }
        return CommonReturnType.create("数据保存成功");
    }

    @Override
    public List<Map<String, String>> getCourseList(String teacherId) {
        List<Map<String, String>> list = teachDetailMapper.selecetCourseList(teacherId);
        return list;
    }

    @Override
    public List<Map<String, Object>> getMyChartData(String teacherId, String courseId, String classNum, int num) throws BusinessException {
        List<Map<String, Object>> list = attendanceMapper.selectChartData(teacherId,courseId,classNum,num);
        return list;
    }

    @Override
    public List<Map<String, Object>> getHistoryList(String teacherId, String startTime, String endTime) {
        List<Map<String, Object>> list = attendanceMapper.selectHistoryData(teacherId,startTime,endTime);
        return list;
    }

    @Override
    public List<Map<String, Object>> getSummaryData() {
        List<Map<String, Object>> list = attendanceMapper.selectSummaryData();
        return list;
    }
}
