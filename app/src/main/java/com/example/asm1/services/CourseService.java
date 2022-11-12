package com.example.asm1.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.asm1.dao.courseDAO;
import com.example.asm1.models.AppCourse;

import java.util.ArrayList;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class CourseService extends IntentService {
    private courseDAO courseDAO;
    public static final String COURSE_SERVICE_EVENT = "COURSE_SERVICE_EVENT";
    public static final String COURSE_SERVICE_ACTION_INSERT = "COURSE_SERVICE_ACTION_INSERT";
    public static final String COURSE_SERVICE_ACTION_UPDATE = "COURSE_SERVICE_ACTION_UPDATE";
    public static final String COURSE_SERVICE_ACTION_DELETE = "COURSE_SERVICE_ACTION_DELETE";
    public static final String COURSE_SERVICE_ACTION_GET_ALL = "COURSE_SERVICE_ACTION_GET_ALL";

    public CourseService() {
        super("CourseService");
        courseDAO = new courseDAO(this);
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent!=null)
        {
            String action = intent.getAction();
            switch(action)
            {
                case COURSE_SERVICE_ACTION_INSERT:
                {
                    String _code=intent.getStringExtra("code");
                    String _name=intent.getStringExtra("name");
                    String _time=intent.getStringExtra("time");
                    String _room=intent.getStringExtra("room");
                    Integer _available = intent.getIntExtra("available", 1);
                    AppCourse course = new AppCourse(-1, _available, _code, _name, _time, _room);
                    Boolean result = courseDAO.insert(course);
                    Intent outIntent = new Intent(COURSE_SERVICE_EVENT);
                    outIntent.putExtra("result", result);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(outIntent);
                    break;
                }
                case COURSE_SERVICE_ACTION_UPDATE:
                {
                    break;
                }
                case COURSE_SERVICE_ACTION_DELETE:
                {
                    Integer id = intent.getIntExtra("id",0);
                    if(id>0)
                    {
                        Boolean res = courseDAO.delete(id);
                        if(res)
                        {
                            ArrayList<AppCourse> result =  courseDAO.getAll();
                            Intent outIntent = new Intent(COURSE_SERVICE_EVENT);
                            outIntent.putExtra("result", result);
                            LocalBroadcastManager.getInstance(this).sendBroadcast(outIntent);
                        }
                    }
                    break;
                }
                case COURSE_SERVICE_ACTION_GET_ALL:
                {
                    ArrayList<AppCourse> result = courseDAO.getAll();
                    Intent outIntent = new Intent(COURSE_SERVICE_EVENT);
                    outIntent.putExtra("result", result);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(outIntent);
                    break;
                }
                default:
                    break;
            }
        }
    }
}