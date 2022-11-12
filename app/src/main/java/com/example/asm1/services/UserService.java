package com.example.asm1.services;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.asm1.dao.userDAO;
import com.example.asm1.models.AppUser;

public class UserService extends IntentService {
    public static final String USER_SERVICE_EVENT = "USER_SERVICE_EVENT";
    public static final String USER_SERVICE_ACTION_LOGIN = "USER_SERVICE_ACTION_LOGIN";
    public static final String USER_SERVICE_ACTION_REGISTER = "USER_SERVICE_ACTION_REGISTER";
    private userDAO userDAO;
    public UserService()
    {
        super("UserService");
        userDAO = new userDAO(this);
    }
    protected void onHandleIntent(@Nullable Intent intent)
    {
        if(intent != null)
        {
            String action = intent.getAction();
            switch (action)
            {
                case USER_SERVICE_ACTION_LOGIN:
                {
                    String email = intent.getStringExtra("email");
                    String password = intent.getStringExtra("password");
                    AppUser appUser = userDAO.login(email, password);
                    Intent outIntent = new Intent(USER_SERVICE_EVENT);
                    outIntent.setAction(USER_SERVICE_ACTION_LOGIN);
                    outIntent.putExtra("result", appUser);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(outIntent);
                    break;
                }
                case USER_SERVICE_ACTION_REGISTER:
                {
                    String email = intent.getStringExtra("email");
                    String password = intent.getStringExtra("password");
                    Integer role = intent.getIntExtra("role", 1);
                    Boolean result = userDAO.register(email, password, role);
                    Intent outIntent = new Intent(USER_SERVICE_EVENT);
                    // outIntent.setAction(USER_SERVICE_ACTION_REGISTER);
                    outIntent.putExtra("result", result);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(outIntent);
                }
            }
        }
    }

}
