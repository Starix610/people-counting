package com.weiyun.peoplecounting.pojo;

public class Course {

    private Integer id;

    private String courseName;

    private Double credits;

    private Integer period;

    private String examWays;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Double getCredits() {
        return credits;
    }

    public void setCredits(Double credits) {
        this.credits = credits;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public String getExamWays() {
        return examWays;
    }

    public void setExamWays(String examWays) {
        this.examWays = examWays;
    }
}