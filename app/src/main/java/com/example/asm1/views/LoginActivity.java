package com.example.asm1.views;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asm1.R;
import com.example.asm1.models.AppUser;
import com.example.asm1.services.UserService;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInOptionsExtension;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    private EditText edtEmail, edtPassword;
    private Button btnLogin, btnRegister;
    GoogleSignInClient gsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        readLogin();


        /**
         * Start: Đăng nhập bằng Gu Gồ
         * Project: https://console.firebase.google.com/u/0/project/projec2-cdad8/settings/general/android:com.example.asm1?nonce=1665390842647
         * Created: 05/10/2022
         * By : Tin Nguyễn
         */
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();

        gsc = GoogleSignIn.getClient(LoginActivity.this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(LoginActivity.this);
        if(account != null)
        {
            Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(homeIntent);
            finish();
        }
        SignInButton sib = findViewById(R.id.btnGoogle);
        sib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gooldIntent = gsc.getSignInIntent();
                googleLauncher.launch(gooldIntent);
            }
        });
        /**
         * End: Đăng nhập bằng Gu Gồ
         */
        /**
         * Đăng nhập bằng Facebook
         */

        /**
         * Đăng nhập bằng Facebook
         */
    }

    // Nhận kết quả Gu Gồ Sai In
    ActivityResultLauncher<Intent> googleLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent data  = result.getData();
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                    try {
                        GoogleSignInAccount account = task.getResult(ApiException.class);
                        String email = account.getEmail();
                        String disPlayName = account.getDisplayName();
                        Log.d(">>>>>>>>>TAG", "onActivityResult: " + email);
                        Log.d(">>>>>>>>>TAG", "onActivityResult: " + disPlayName);
                        //Lưu database
                        Integer role = 1;
                        Intent intent= new Intent(getBaseContext(), UserService.class);
                        intent.putExtra("email",email);
                        intent.putExtra("password","1");
                        intent.putExtra("role",role);
                        intent.setAction(UserService.USER_SERVICE_ACTION_REGISTER);
                        startService(intent);
                        //Lưu database
                        Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(homeIntent);
                        finish();
                    }catch (Exception e)
                    {
                        Log.d(">>>>>>>>TAG", "onActivityResult error: " + e.getMessage());
                    }
                }
            }
    );
    //Nhận kết quả của Gu Gồ
    public void onRegisterClick(View view)
    {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
    public void onLoginClick(View view)
    {

        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        Intent intent = new Intent(this, UserService.class);
        intent.setAction(UserService.USER_SERVICE_ACTION_LOGIN);
        intent.putExtra("email", email);
        intent.putExtra("password", password);
        startService(intent);
        Intent i = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(i);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(loginReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        readLogin();
        IntentFilter loginFilter = new IntentFilter(UserService.USER_SERVICE_EVENT);
        LocalBroadcastManager.getInstance(this).registerReceiver(loginReceiver, loginFilter);
    }
    BroadcastReceiver loginReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            AppUser result = (AppUser) intent.getSerializableExtra("result");
            if(result != null)
            {
                writeLogin(result);// lưu đăng nhập
                Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(homeIntent);
            }
            else
            {
                Toast.makeText(LoginActivity.this, "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
            }
        }
    };
    private void writeLogin(AppUser appUser)
    {
        SharedPreferences sharedPreferences = getSharedPreferences("LOGIN_STATUS", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", true);
        editor.putString("email", appUser.getEmail());
        editor.putInt("role", appUser.getRole());
        editor.putInt("id", appUser.getId());
        editor.commit();
    }
    private void readLogin()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("LOGIN_STATUS", MODE_PRIVATE);
        Boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        if(isLoggedIn)
        {
            Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(homeIntent);
        }
    }
}