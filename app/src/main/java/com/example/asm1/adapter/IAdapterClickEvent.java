package com.example.asm1.adapter;

import com.example.asm1.models.AppCourse;

public interface IAdapterClickEvent {
    public void onEditCourseClick(AppCourse course);
    public void onDeleteCourseClick(AppCourse course);
}
