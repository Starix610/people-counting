package com.weiyun.peoplecounting.controller;

import com.weiyun.peoplecounting.error.BusinessException;
import com.weiyun.peoplecounting.error.EmBusinessError;
import com.weiyun.peoplecounting.pojo.*;
import com.weiyun.peoplecounting.response.CommonReturnType;
import com.weiyun.peoplecounting.service.ComboxDataService;
import com.weiyun.peoplecounting.service.TeachDetailService;
import com.weiyun.peoplecounting.service.TeacherInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/bind")
@Api(description = "教师信息绑定接口")
public class InfoBindController extends BaseController {

    @Autowired
    private TeacherInfoService teacherInfoService;
    @Autowired
    private TeachDetailService teachDetailService;
    @Autowired
    private ComboxDataService comboxDataService;

    @ApiOperation(value = "添加教师认证信息", notes = "填写教师认证信息")
    @PostMapping("/addTeacherInfo")
    public CommonReturnType addTeacherInfo (@RequestBody @ApiParam(name = "teacher",value = "教师信息实体,教师ID和教师姓名为必填参数",required = true) Teacher teacher, HttpSession session) throws BusinessException {

        if (StringUtils.isEmpty(teacher.getId())){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION, "教师ID不能为空");
        }
        if (StringUtils.isEmpty(teacher.getTeacherName())){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION, "教师姓名不能为空");
        }
        UserVO user = (UserVO) session.getAttribute("user");
        CommonReturnType result = teacherInfoService.addTeacherInfo(teacher,user.getId());
        user.setTeacherId(teacher.getId());  //更新session中的user对象
        return result;
    }

    @ApiOperation(value = "添加我的课程", notes = "添加教师授课的课程")
    @PostMapping("/addCourse")
    public CommonReturnType addCourse(@RequestBody TeachDetail course, HttpSession session) throws BusinessException {

        UserVO user = (UserVO) session.getAttribute("user");
        String teacherId = user.getTeacherId();
        if (teacherId == null){
            throw new BusinessException(EmBusinessError.TEACHER_INFO_NOT_BIND);
        }
        CommonReturnType result = teachDetailService.addTeachAnfo(course,teacherId);
        return result;
    }

    @ApiOperation(value = "获得所有课程", notes = "所有课程下拉列表数据")
    @GetMapping("/courses")
    public CommonReturnType getCourses(){
        List<Course> courseList = comboxDataService.getCourses();
        return CommonReturnType.create(courseList);
    }

    @ApiOperation(value = "获得所有学院", notes = "所有学院下拉列表数据")
    @GetMapping("/colleges")
    public CommonReturnType getColleges(){
        List<College> collegeList = comboxDataService.getColleges();
        return CommonReturnType.create(collegeList);
    }

}
