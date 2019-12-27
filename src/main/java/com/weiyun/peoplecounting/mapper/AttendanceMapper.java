package com.weiyun.peoplecounting.mapper;

import com.weiyun.peoplecounting.pojo.Attendance;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AttendanceMapper {
    int deleteByPrimaryKey(String id);

    int insert(Attendance record);

    Attendance selectByPrimaryKey(String id);

    List<Attendance> selectAll();

    int updateByPrimaryKey(Attendance record);

    Attendance selectToday(@Param("teachDetailId") String teachDetailId);

    int updateAttendance(Attendance attendance);

    List<Map<String, Object>> selectChartData(@Param("teacherId")String teacherId, @Param("courseId")String courseId, @Param("classNum")String classNum, @Param("num")int num);

    List<Map<String, Object>> selectHistoryData(@Param("teacherId")String teacherId, @Param("startTime")String startTime, @Param("endTime")String endTime);

    List<Map<String, Object>> selectSummaryData();
}