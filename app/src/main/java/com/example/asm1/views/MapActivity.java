package com.example.asm1.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asm1.R;
import com.example.asm1.fragments.MapFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class MapActivity extends AppCompatActivity {

    private EditText edtAddress;
    FusedLocationProviderClient mfusedLocationProviderClient;
    Location mLastLocation;
    Button btnMyLocation;
    TextView txtLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        edtAddress = findViewById(R.id.edtAddress);
        btnMyLocation =findViewById(R.id.btnMyLocation);
        txtLocation =findViewById(R.id.txtLocation);

        mfusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        btnMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });

    }
    public void onShowMapClick(View view){
        String address = edtAddress.getText().toString();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flMap, MapFragment.newInstance(address))
                .commit();
    }
    public void getLocation(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            //yÊU cầu người dùng cấp quyền truy cập location khi chưa đc phép
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},9999);
        }else {
            //thực hiện việc lấy Location khi đã có quyền
            mfusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location != null){
                        mLastLocation = location;
                        String thongtin =   mLastLocation.getLatitude() + ", " + mLastLocation.getLongitude();
                        Toast.makeText(MapActivity.this, "", Toast.LENGTH_SHORT).show();
                        txtLocation.setText(thongtin);
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.flMap, MapFragment.newInstance(thongtin))
                                .commit();

                    }else {
                        txtLocation.setText("Hông lấy được vị trí");
                    }
                }
            });
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 9999){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLocation();
            }else {
                Toast.makeText(this, "Từ chối", Toast.LENGTH_SHORT).show();
            }
        }
    }
}