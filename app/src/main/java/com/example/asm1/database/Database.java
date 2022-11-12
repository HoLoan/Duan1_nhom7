package com.example.asm1.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {
    private static Database instance;
    public static synchronized Database getInstance(Context context)
    {
        if(instance==null)

            instance = new Database(context);
            return instance;

    }
    private Database(Context context)
    {
        super(context, "MYDATABSE", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlUser = "CREATE TABLE IF NOT EXISTS USERS(ID INTEGER PRIMARY KEY AUTOINCREMENT, EMAIL TEXT, PASSWORD TEXT, ROLE INTEGER)";
        db.execSQL(sqlUser);

        String sqlCourses = "CREATE TABLE IF NOT EXISTS COURSES(ID INTEGER PRIMARY KEY AUTOINCREMENT, CODE TEXT, NAME TEXT, TIME TEXT, ROOM TEXT, AVAILABLE INTEGER)";
        db.execSQL(sqlCourses);

        String sqlEnrolls = "CREATE TABLE IF NOT EXISTS ENROLLS(ID INTEGER PRIMARY KEY AUTOINCREMENT, STUDENT_ID INTEGER, COURSE_ID INTEGER, JOINED FLOAT, FOREIGN KEY (STUDENT_ID) REFERENCES USERS(ID), FOREIGN KEY (COURSE_ID) REFERENCES COURSES(ID))";
        db.execSQL(sqlEnrolls);

        db.execSQL("insert into USERS (EMAIL, PASSWORD, ROLE) values ('cinsall0@google.com', 'UKzVwOtnVuWc', 2);");
        db.execSQL("insert into USERS (EMAIL, PASSWORD, ROLE) values ('manstie1@wiley.com', 'gB7c5WLBV', 2);");
        db.execSQL("insert into COURSES (CODE, NAME, TIME,ROOM,AVAILABLE) values ('MOP111', 'LAP TRINH HTML', '07:00 -09:00','Room 017',1)");
        db.execSQL("insert into COURSES (CODE, NAME, TIME,ROOM,AVAILABLE) values ('MOP111', 'LAP TRINH JAVA', '07:00 -09:00','Room 027',1)");
        db.execSQL("insert into COURSES (CODE, NAME, TIME,ROOM,AVAILABLE) values ('MOP111', 'LAP TRINH C', '07:00 -09:00','Room 037',1)");
        db.execSQL("insert into COURSES (CODE, NAME, TIME,ROOM,AVAILABLE) values ('MOP111', 'LAP TRINH PYTHON', '07:00 -09:00','Room 047',1)");
        db.execSQL("insert into ENROLLS (STUDENT_ID, COURSE_ID, JOINED) values (1, 1, 72);");
        db.execSQL("insert into ENROLLS (STUDENT_ID, COURSE_ID, JOINED) values (2, 2, 83);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion)
        {
            db.execSQL("DROP TABLE IF EXISTS USERS");
            db.execSQL("DROP TABLE IF EXISTS COURSES");
            db.execSQL("DROP TABLE IF EXISTS ENROLLS");
            onCreate(db);
        }
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }
}
