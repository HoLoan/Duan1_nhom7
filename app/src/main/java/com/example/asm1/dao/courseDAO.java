package com.example.asm1.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.asm1.database.Database;
import com.example.asm1.models.AppCourse;
import com.example.asm1.models.AppUser;

import java.util.ArrayList;

public class courseDAO {
    private Database database;
    public courseDAO(Context context)
    {
        database = Database.getInstance(context);
    }

    public ArrayList<AppCourse> getAll()
    {
        ArrayList<AppCourse> list = new ArrayList<>();

        SQLiteDatabase db = database.getReadableDatabase();
        String sql = "SELECT ID, CODE, NAME, TIME, ROOM, AVAILABLE FROM COURSES";
        Cursor c = db.rawQuery(sql, null);
        try
        {
            if(c.moveToFirst())
            {
                while(!c.isAfterLast())
                {
                    Integer _id = c.getInt(0);
                    String _code = c.getString(1);
                    String _name = c.getString(2);
                    String _time = c.getString(3);
                    String _room = c.getString(4);
                    Integer _available = c.getInt(5);
                    AppCourse course = new AppCourse(_id, _available, _code, _name, _time, _room);
                    list.add(course);
                    c.moveToNext();
                }
            }
        }
        catch (Exception e)
        {
            Log.d(">>>>>>>>>TAG", "getAll: " + e.getMessage());
        }
        finally {
            if(c != null && !c.isClosed()) c.close();
        }
        return list;
    }
    public Boolean insert(AppCourse course)
    {
        Boolean result = false;
        SQLiteDatabase db =database.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put("CODE", course.getCode());
            values.put("NAME", course.getName());
            values.put("TIME", course.getTime());
            values.put("ROOM", course.getRoom());
            values.put("AVAILABLE", course.getAvailable());
            long rows = db.insertOrThrow("COURSES", null, values);
            result = rows >= 0;
            db.setTransactionSuccessful();//Thành công, lưu
        }
            catch (Exception e)
            {
                Log.d(">>>>>>>>>TAG", "Update: " + e.getMessage());
            }
            finally
            {
                db.endTransaction();
            }

        return result;
    }
    public Boolean update(AppCourse course)
    {
        Boolean result = false;
        SQLiteDatabase db =database.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put("CODE", course.getCode());
            values.put("NAME", course.getName());
            values.put("TIME", course.getTime());
            values.put("ROOM", course.getRoom());
            values.put("AVAILABLE", course.getAvailable());
            long rows = db.update("COURSES", values, "ID = ?",
                    new String[]{course.getId().toString()});
            result = rows >= 0;
            db.setTransactionSuccessful();//Thành công, lưu
        }
        catch (Exception e)
        {
            Log.d(">>>>>>>>>TAG", "Update: " + e.getMessage());
        }
        finally
        {
            db.endTransaction();
        }
        return  result;
    }
    public Boolean delete(Integer id)
    {
        Boolean result = false;
        SQLiteDatabase db =database.getWritableDatabase();
        db.beginTransaction();
        try {

            long rows = db.delete("COURSES", "ID = ?",
                    new String[]{id.toString()});
            result = rows >= 0;
            db.setTransactionSuccessful();//Thành công, lưu
        }
        catch (Exception e)
        {
            Log.d(">>>>>>>>>TAG", "Update: " + e.getMessage());
        }
        finally
        {
            db.endTransaction();
        }
        return  result;
    }
}
