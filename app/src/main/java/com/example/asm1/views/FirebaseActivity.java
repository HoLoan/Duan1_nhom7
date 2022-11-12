package com.example.asm1.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asm1.R;
import com.example.asm1.adapter.IAdapterClickEvent;
import com.example.asm1.fragments.CourseFragment;
import com.example.asm1.models.AppCourse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FirebaseActivity extends AppCompatActivity  implements IAdapterClickEvent {
    EditText edtCoursename, edtCourseCode, edtCourseTime, edtCourseRoom;
    Button btnSave, btnCancel;
    FirebaseFirestore db;
    private AppCourse appCourse = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);
        edtCoursename = findViewById(R.id.edtCoursename);
        edtCourseCode = findViewById(R.id.edtCourseCode);
        edtCourseTime = findViewById(R.id.edtCourseTime);
        edtCourseRoom = findViewById(R.id.edtCourseRoom);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        db = FirebaseFirestore.getInstance();
        getData();
    }
    public void onSaveClick(View view)
    {
        String name = edtCoursename.getText().toString();
        String code = edtCourseCode.getText().toString();
        String time = edtCourseTime.getText().toString();
        String room = edtCourseRoom.getText().toString();

        // Create a new user with a first and last name
        Map<String, Object> item = new HashMap<>();
        item.put("code", code);
        item.put("name", name);
        item.put("time", time);
        item.put("room", room);
        if(appCourse == null)
        {
            db.collection("courses")
                    .add(item)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(FirebaseActivity.this, "Đã chèn", Toast.LENGTH_SHORT).show();
                            getData();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure (@NonNull Exception e){
                            Toast.makeText(FirebaseActivity.this, "Chèn thất bại!!!", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        else
        {
            db.collection("courses")
                    .document(appCourse.getCourseId())
                    .set(item)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void avoid) {
                            onCancelClick(null);
                            Toast.makeText(FirebaseActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                            getData();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure (@NonNull Exception e){
                            Toast.makeText(FirebaseActivity.this, "Cập nhật thất bại!!!", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

// Add a new document with a generated ID
    }
    public void onCancelClick(View view)
    {
        edtCoursename.setText(null);
        edtCourseCode.setText(null);
        edtCourseTime.setText(null);
        edtCourseRoom.setText(null);

    }
    private void getData()
    {
        db.collection("courses")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<AppCourse> list = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> map = document.getData();
                                String code = map.get("code").toString();
                                String name = map.get("name").toString();
                                String time = map.get("time").toString();
                                String room = map.get("room").toString();
                                AppCourse course = new AppCourse(-1, 1, code, name, time, room);
                                course.setCourseId(document.getId());
                                list.add(course);
                            }
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.frl, CourseFragment.newInstance(list))
                                    .commit();
                        }
                    }
                });
    }
    @Override
    public void onEditCourseClick(AppCourse course) {
        edtCoursename.setText(course.getName());
        edtCourseCode.setText(course.getCode());
        edtCourseTime.setText(course.getTime());
        edtCourseRoom.setText(course.getRoom());
        appCourse = course;
    }

    @Override
    public void onDeleteCourseClick(AppCourse course) {
        new AlertDialog.Builder(FirebaseActivity.this)
                .setTitle("Xoá").setMessage("Xoá sẽ không thế phục hồi!!")
                .setNegativeButton("Huỷ", null)
                .setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.collection("courses").document(course.getCourseId())
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(FirebaseActivity.this, "Đã xoá", Toast.LENGTH_SHORT).show();
                                        getData();

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(FirebaseActivity.this, "Xoá thất bại!!!", Toast.LENGTH_SHORT).show();

                                    }
                                });
                    }
                }).show();
    }
}