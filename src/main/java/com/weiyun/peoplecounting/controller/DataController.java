package com.weiyun.peoplecounting.controller;

import com.weiyun.peoplecounting.error.BusinessException;
import com.weiyun.peoplecounting.error.EmBusinessError;
import com.weiyun.peoplecounting.pojo.TeachDetailVO;
import com.weiyun.peoplecounting.pojo.UserVO;
import com.weiyun.peoplecounting.response.CommonReturnType;
import com.weiyun.peoplecounting.service.ComboxDataService;
import com.weiyun.peoplecounting.service.DataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/data")
@Api(description = "数据展示接口")
public class DataController extends BaseController {


    @Autowired
    private ComboxDataService comboxDataService;
    @Autowired
    private DataService dataService;

    @ApiOperation(value = "我的课程下拉列表数据", notes = "返回自己的课程的下拉列表数据")
    @GetMapping("/mycourses")
    public CommonReturnType getMyCourses(HttpSession session) throws BusinessException {
        UserVO user = (UserVO) session.getAttribute("user");
        String teacherId = user.getTeacherId();
        if (StringUtils.isEmpty(teacherId)){
            throw new BusinessException(EmBusinessError.TEACHER_INFO_NOT_BIND);
        }
        List<TeachDetailVO> resultList = comboxDataService.getCoursesByTeacherId(teacherId);
        if (resultList == null || resultList.size() <= 0){
            throw new BusinessException(EmBusinessError.NO_COURSE_ADDED);
        }
        return CommonReturnType.create(resultList);
    }


    @ApiOperation(value = "识别数据保存", notes = "识别数据保存")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teachDetailId",value = "授课详情ID",paramType = "query"),
            @ApiImplicitParam(name = "count",value = "统计人数",paramType = "query"),
            @ApiImplicitParam(name = "orginalImg",value = "原始图片url",paramType = "query"),
            @ApiImplicitParam(name = "detectionImg",value = "检测后图片url",paramType = "query")
    }
    )
    @PostMapping("/save")
    public CommonReturnType saveData(String teachDetailId, Integer count, String orginalImg, String detectionImg) throws BusinessException {
        if (StringUtils.isEmpty(teachDetailId)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION,"缺少teachDetailId参数");
        }
        CommonReturnType result =  dataService.saveData(teachDetailId,count,orginalImg,detectionImg);
        return result;
    }


    @ApiOperation(value = "获取我的统计数据课程列表", notes = "获取我的统计数据课程列表")
    @GetMapping("/mychartlist")
    public CommonReturnType getMyList(HttpSession session) throws BusinessException {
        UserVO user = (UserVO) session.getAttribute("user");
        String teacherId = user.getTeacherId();
        if (StringUtils.isEmpty(teacherId)){
            throw new BusinessException(EmBusinessError.TEACHER_INFO_NOT_BIND);
        }
        List<Map<String, String>> list =  dataService.getCourseList(teacherId);
        if (list == null || list.size() <=0 ){
            throw new BusinessException(EmBusinessError.NO_COURSE_ADDED);
        }
        return CommonReturnType.create(list);
    }

    @ApiOperation(value = "获取某项课程的统计数据详情", notes = "获取某项课程的统计数据详情，用于绘制图表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseId",value = "课程ID",paramType = "query"),
            @ApiImplicitParam(name = "classNum",value = "班级序号",paramType = "query"),
            @ApiImplicitParam(name = "num",value = "获取最近num次课程的数据",paramType = "query")
    }
    )
    @GetMapping("/mychartdata")
    public CommonReturnType getMyChartData(String courseId,String classNum,Integer num, HttpSession session) throws BusinessException {
        UserVO user = (UserVO) session.getAttribute("user");
        String teacherId = user.getTeacherId();

        //返回选中选中课程的统计详情(出勤率，出勤人数，总人数,教室,班级序号，星期几，第几节，保存时间)
        //获取最近num次课程的数据
        List<Map<String,Object>> list = dataService.getMyChartData(teacherId,courseId,classNum,num);
        if (list == null || list.size()<=0){
            throw new BusinessException(EmBusinessError.NO_ATTENDANCE_ADDED);
        }
        return CommonReturnType.create(list);
    }

    @ApiOperation(value = "获取所有课程汇总数据", notes = "返回所有课程汇总数据(每一门课程的平均出勤率)")
    @GetMapping("/summarydata")
    public CommonReturnType getSummaryData() throws BusinessException {

        List<Map<String,Object>> list = dataService.getSummaryData();
        if (list == null || list.size()<=0){
            throw new BusinessException(EmBusinessError.NO_DATA);
        }
        return CommonReturnType.create(list);
    }

    @ApiOperation(value = "获取历史记录列表", notes = "获取历史记录列表")
    @GetMapping("/historylist")
    public CommonReturnType getHistoryList(String startTime, String endTime,HttpSession session) throws BusinessException {
        UserVO user = (UserVO) session.getAttribute("user");
        String teacherId = user.getTeacherId();
        if (StringUtils.isEmpty(teacherId)){
            throw new BusinessException(EmBusinessError.TEACHER_INFO_NOT_BIND);
        }

        //对时间参数进行处理，方便mybatis中的进行时间筛选的动态sql书写
        startTime = "".equals(startTime)?null:startTime;
        endTime = "".equals(endTime)?null:endTime;

        //返回列表(原图，检测图，课程名，出勤率，出勤人数，总人数,教室,班级序号，星期几，第几节，保存时间)
        List<Map<String,Object>> list = dataService.getHistoryList(teacherId,startTime,endTime);
        if (list == null || list.size()<=0){
            throw new BusinessException(EmBusinessError.NO_ATTENDANCE_ADDED);
        }
        return CommonReturnType.create(list);
    }


}
