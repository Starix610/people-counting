package com.weiyun.peoplecounting.mapper;

import com.weiyun.peoplecounting.pojo.TeachDetail;
import com.weiyun.peoplecounting.pojo.TeachDetailVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TeachDetailMapper {

    int deleteByPrimaryKey(String id);

    int insert(TeachDetail record);

    TeachDetail selectByPrimaryKey(String id);

    List<TeachDetail> selectAll();

    int updateByPrimaryKey(TeachDetail record);

    int selectToCheckExist(@Param("teacherId") String teacherId, @Param("courseId") String courseId,  @Param("weekday")String weekday, @Param("sequence") String sequence);

    List<TeachDetailVO> selectTeachDetailVo(@Param("teacherId") String teacherId);

    List<Map<String, String>> selecetCourseList(@Param("teacherId")String teacherId);
}