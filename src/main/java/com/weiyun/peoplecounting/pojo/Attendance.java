package com.weiyun.peoplecounting.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Attendance {

    private String id;

    private String teachDetailId;

    private String classroom;

    private Integer attendanceNum;

    private Integer totalNumber;

    private Double attendanceRate;
    //这里使用定制配置，application.properties里的是全局默认配置
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date recordTime;

    private String originalImage;

    private String detectionImage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeachDetailId() {
        return teachDetailId;
    }

    public void setTeachDetailId(String teachDetailId) {
        this.teachDetailId = teachDetailId;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public Integer getAttendanceNum() {
        return attendanceNum;
    }

    public void setAttendanceNum(Integer attendanceNum) {
        this.attendanceNum = attendanceNum;
    }

    public Integer getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Integer totalNumber) {
        this.totalNumber = totalNumber;
    }

    public Double getAttendanceRate() {
        return attendanceRate;
    }

    public void setAttendanceRate(Double attendanceRate) {
        this.attendanceRate = attendanceRate;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public String getOriginalImage() {
        return originalImage;
    }

    public void setOriginalImage(String originalImage) {
        this.originalImage = originalImage;
    }

    public String getDetectionImage() {
        return detectionImage;
    }

    public void setDetectionImage(String detectionImage) {
        this.detectionImage = detectionImage;
    }
}