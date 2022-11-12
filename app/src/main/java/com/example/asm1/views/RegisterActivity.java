package com.example.asm1.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asm1.R;
import com.example.asm1.services.UserService;

public class RegisterActivity extends AppCompatActivity {
    private EditText edtEmail, edtPassword;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnRegister = findViewById(R.id.btnRegister);
    }
    public void onRegisterClick(View view)
    {
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        Integer role = 1;
        Intent intent = new Intent(this, UserService.class);
        intent.putExtra("email", email);
        intent.putExtra("password", password);
        intent.putExtra("role", role);
        intent.setAction(UserService.USER_SERVICE_ACTION_REGISTER);
        startService(intent);
        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(i);

    }

    @Override
    protected void onPause() {
        super.onPause();
        IntentFilter inf = new IntentFilter(UserService.USER_SERVICE_EVENT);
        LocalBroadcastManager.getInstance(this).registerReceiver(registerReciver, inf);

    }

    @Override
    protected void onResume() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(registerReciver);
        super.onResume();
    }

    private BroadcastReceiver registerReciver =  new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
                Boolean result = intent.getBooleanExtra("result", false);
                if (result)
                {
                    Toast.makeText(RegisterActivity.this, "Đăng kí thành công", Toast.LENGTH_LONG).show();
                    finish();
                }
                else
                {
                    Toast.makeText(RegisterActivity.this, "Đăng kí không thành công!!!", Toast.LENGTH_LONG).show();
                }
        }
    };
}