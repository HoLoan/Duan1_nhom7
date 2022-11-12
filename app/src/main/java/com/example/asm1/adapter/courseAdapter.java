package com.example.asm1.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.asm1.R;
import com.example.asm1.models.AppCourse;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class courseAdapter extends BaseAdapter {
    private ArrayList<AppCourse> list;
    public courseAdapter(ArrayList<AppCourse> list)
    {
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int _i, View _view, ViewGroup _viewGroup) {
        View view = _view;
        if(view ==null)
        {
            view = View.inflate(_viewGroup.getContext(), R.layout.layout_courses, null);
            TextView courseCode = view.findViewById(R.id.courseCode);
            TextView courseName = view.findViewById(R.id.courseName);
            TextView courseTime = view.findViewById(R.id.courseTime);
            TextView courseRoom = view.findViewById(R.id.courseRoom);
            ImageButton btnEdit =  view.findViewById(R.id.btnEdit);
            ImageButton btnDelete =  view.findViewById(R.id.btnDelete);
            ViewHolder holder = new ViewHolder(courseCode, courseName, courseTime, courseRoom, btnEdit, btnDelete);
            view.setTag(holder);
        }
        AppCourse course = (AppCourse) getItem(_i);
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.courseCode.setText(course.getCode());
        holder.courseCode.setText(course.getName());
        holder.courseCode.setText(course.getTime());
        holder.courseCode.setText(course.getRoom());
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IAdapterClickEvent iAdapterClickEvent = (IAdapterClickEvent) _viewGroup.getContext();
                iAdapterClickEvent.onEditCourseClick(course);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IAdapterClickEvent iAdapterClickEvent = (IAdapterClickEvent) _viewGroup.getContext();
                iAdapterClickEvent.onDeleteCourseClick(course);
            }
        });

        return view;
    }
    private static class ViewHolder
    {
        final TextView courseCode, courseName, courseTime, courseRoom;
        final ImageButton btnEdit, btnDelete;
        public ViewHolder(TextView courseCode, TextView courseName, TextView courseTime, TextView courseRoom
        ,ImageButton btnEdit, ImageButton btnDelete)
        {
            this.courseCode=courseCode;
            this.courseName=courseName;
            this.courseTime=courseTime;
            this.courseRoom=courseRoom;
            this.btnEdit = btnEdit;
            this.btnDelete = btnDelete;
        }
    }
}
