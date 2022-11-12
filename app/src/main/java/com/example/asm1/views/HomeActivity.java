package com.example.asm1.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.asm1.R;
import com.example.asm1.adapter.IAdapterClickEvent;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Arrays;
import java.util.Calendar;

public class HomeActivity extends AppCompatActivity  {
    private ImageView img;
    private Button btnNews, btnXem, btnMap, btnDate, btnLogout;
    GoogleSignInAccount account;
    GoogleSignInClient gsc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnNews = findViewById(R.id.btnNews);
        btnXem = findViewById(R.id.btnXem);
        btnMap = findViewById(R.id.btnMap);
        btnDate = findViewById(R.id.btnHomeShare);
        btnLogout =findViewById(R.id.btnLogout);
        img = findViewById(R.id.imageView);

        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();

        gsc = GoogleSignIn.getClient(HomeActivity.this, gso);
        account = GoogleSignIn.getLastSignedInAccount(HomeActivity.this);
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(1664759160);
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH);
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
//        int hour = calendar.get(Calendar.HOUR);
//        int minute = calendar.get(Calendar.MINUTE);
//        int second = calendar.get(Calendar.SECOND);
    }
//        public void onDangkiClick(View view)
//        {
//            Intent t = new Intent(HomeActivity.this, RegisterActivity.class);
//            startActivity(t);
//        }
    public void onHomeShareClick(View view)
    {
        Intent i = new Intent(HomeActivity.this, MXHActivity.class);
        startActivity(i);
    }
        public void onMapClick(View view)
        {
            Intent i = new Intent(HomeActivity.this, MapActivity.class);
            startActivity(i);
        }
        public void onNewsClick(View view)
        {
            Intent i = new Intent(HomeActivity.this, NewsActivity.class);
            startActivity(i);
        }
        public void onXemClick(View view)
        {
            Intent t = new Intent(HomeActivity.this, FirebaseActivity.class);
            startActivity(t);
        }
        public void onLogoutClick(View view)
        {
            if(account != null)
            {
                gsc.signOut().addOnCompleteListener(HomeActivity.this,
                        new OnCompleteListener<Void>(){
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
            }
            else
            {
                SharedPreferences sharedPreferences = getSharedPreferences("LOGIN_STATUS", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("isLoggedIn");
                editor.remove("email");
                editor.remove("role");
                editor.remove("id");
                editor.apply();
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }

}