package com.example.asm1.models;

import java.io.Serializable;

public class AppCourse implements Serializable {
    private Integer id, available;
    private String code, name, time, room, courseId;

    public AppCourse()
    {

    }

    public AppCourse(Integer id, Integer available, String code, String name, String time, String room) {
        this.id = id;
        this.available = available;
        this.code = code;
        this.name = name;
        this.time = time;
        this.room = room;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
