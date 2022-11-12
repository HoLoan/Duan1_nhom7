package com.example.asm1.models;

import java.io.Serializable;

public class AppEnroll implements Serializable {
    private Integer id, courseId, studentId;
    private Float joined;
    public AppEnroll()
    {

    }

    public AppEnroll(Integer id, Integer courseId, Integer studentId, Float joined) {
        this.id = id;
        this.courseId = courseId;
        this.studentId = studentId;
        this.joined = joined;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Float getJoined() {
        return joined;
    }

    public void setJoined(Float joined) {
        this.joined = joined;
    }
}
