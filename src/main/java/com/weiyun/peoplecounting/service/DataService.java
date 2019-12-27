package com.weiyun.peoplecounting.service;

import com.weiyun.peoplecounting.error.BusinessException;
import com.weiyun.peoplecounting.response.CommonReturnType;

import java.util.List;
import java.util.Map;


public interface DataService {

    CommonReturnType saveData(String teachDetailId, Integer count, String orginalImg, String detectionImg) throws BusinessException;

    List<Map<String, String>> getCourseList(String teacherId);

    List<Map<String, Object>> getMyChartData(String teacherId, String courseId1, String classNum, int num) throws BusinessException;

    List<Map<String, Object>> getHistoryList(String teacherId, String startTime, String endTime);

    List<Map<String, Object>> getSummaryData();
}
