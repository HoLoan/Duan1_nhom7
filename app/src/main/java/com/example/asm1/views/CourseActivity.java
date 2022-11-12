package com.example.asm1.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asm1.R;
import com.example.asm1.adapter.IAdapterClickEvent;
import com.example.asm1.adapter.courseAdapter;
import com.example.asm1.fragments.CourseFragment;
import com.example.asm1.models.AppCourse;
import com.example.asm1.services.CourseService;
import com.example.asm1.services.UserService;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CourseActivity extends AppCompatActivity {
//    private ListView lvCourses;
    TextView tvTitle;
    courseAdapter courseAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
//        lvCourses = findViewById(R.id.lvCourses);
//        tvTitle = findViewById(R.id.tvTitle);
    }

    private void onGetCourses()
    {
        Intent intent = new Intent(this, CourseService.class);
        intent.setAction(CourseService.COURSE_SERVICE_ACTION_GET_ALL);
        startService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter courseFilter = new IntentFilter(CourseService.COURSE_SERVICE_EVENT);
        LocalBroadcastManager.getInstance(this).registerReceiver(courseReceiver, courseFilter);
        onGetCourses();
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(courseReceiver);
    }
    BroadcastReceiver courseReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<AppCourse> result =  (ArrayList<AppCourse>) intent.getSerializableExtra("result");
//            courseAdapter = new courseAdapter(list);
//            lvCourses.setAdapter(courseAdapter);
            getSupportFragmentManager().beginTransaction().replace(R.id.fl, CourseFragment.newInstance(result))
                    .commit();
        }
    };
    public void onCourseItemClick(AppCourse course)
    {
        Log.d(">>>>>>>>>TAG", "onCourseItemClick: " + course.getName());
        new AlertDialog.Builder(this)
                .setTitle("Thông báo")
                .setMessage("Xoá sẽ không phục hồi được!")
                .setIcon(R.drawable.ic_launcher_background)
                .setPositiveButton("đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(CourseActivity.this, CourseService.class);
                        intent.setAction(CourseService.COURSE_SERVICE_ACTION_DELETE);
                        intent.putExtra("id", course.getId());
                        startService(intent);
                    }
                }).setNegativeButton("Huỷ", null).show();

    }


}