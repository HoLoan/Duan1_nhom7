package com.example.asm1.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.example.asm1.R;
import com.example.asm1.adapter.courseAdapter;
import com.example.asm1.models.AppCourse;
import com.example.asm1.views.CourseActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CourseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CourseFragment extends Fragment {
    private ArrayList<AppCourse> courses;
    private ListView lvCourses;
    private FrameLayout fl;


    public CourseFragment() {
        // Required empty public constructor
    }
    // nhan du lieu tu ben ngoai
    public static CourseFragment newInstance(ArrayList<AppCourse> courses) {
        CourseFragment fragment = new CourseFragment();
        Bundle args = new Bundle();
        args.putSerializable("courses", courses);
        fragment.setArguments(args);
        return fragment;
    }
    // doc du lieu truyen vao
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            courses = (ArrayList<AppCourse>) getArguments().getSerializable("courses");
        }
    }
    // load layout
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course, container, false);
    }
    // xu ly logic
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lvCourses = view.findViewById(R.id.lvCourses);
        courseAdapter adapter = new courseAdapter(courses);
        lvCourses.setAdapter(adapter);
        lvCourses.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AppCourse course = (AppCourse) parent.getItemAtPosition(position);
                // truyền ra activity
                CourseActivity activity = (CourseActivity) view.getContext();
                activity.onCourseItemClick(course);
                return true;
            }
        });
    }
}