package com.weiyun.peoplecounting.pojo;

import io.swagger.annotations.ApiModelProperty;

public class Teacher {

    @ApiModelProperty(value = "教师ID号",required = true)
    private String id;

    @ApiModelProperty(value = "教师姓名",required = true)
    private String teacherName;

    @ApiModelProperty(value = "性别(0-男，1-女)")
    private Byte gender;

    @ApiModelProperty(value = "教师职称")
    private String title;

    @ApiModelProperty(value = "所属学院id",required = true)
    private String collegeId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(String collegeId) {
        this.collegeId = collegeId;
    }
}