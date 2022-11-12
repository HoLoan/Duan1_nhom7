package com.example.asm1.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.webkit.WebViewDatabase;

import com.example.asm1.models.AppUser;
import com.example.asm1.database.Database;

public class userDAO {
    private Database database;
    public userDAO(Context context)
    {
        database = Database.getInstance(context);
    }
    public AppUser login(String email, String password)
    {
        AppUser appUser = null;
        SQLiteDatabase db = database.getReadableDatabase();
        String sql = "SELECT ID, EMAIL, PASSWORD, ROLE FROM USERS WHERE EMAIL = ?";
        Cursor c = db.rawQuery(sql, new String[]{email});
        try
        {
            if(c.moveToFirst())
            {
                while(!c.isAfterLast())
                {
                    Integer _id = c.getInt(0);
                    String _email = c.getString(1);
                    String _password = c.getString(2);
                    Integer _role = c.getInt(3);
                    if(_password.equals(password)) break;
                    appUser = new AppUser(_id, _role, _email, null);
                    c.moveToNext();
                }
            }
        }
        catch (Exception e)
        {
            Log.d(">>>>>>>>>TAG", "login: " + e.getMessage());
        }
        finally {
            if(c != null && !c.isClosed()) c.close();
        }
        return appUser;
    }
    public Boolean register(String email, String password, Integer role)
    {
        Boolean result = false;
        SQLiteDatabase db = database.getWritableDatabase();
        db.beginTransaction(); //Bắt đầu tương tác db
        try
        {
            ContentValues values = new ContentValues();
            values.put("EMAIL", email);
            values.put("PASSWORD", password);
            values.put("ROLE", role);
            long rows = db.insertOrThrow("USERS", null, values);
            result = rows>=1;
            db.setTransactionSuccessful();
        }
        catch (Exception e)
        {
            Log.d(">>>>>>>>>>TAG", "register: " + e.getMessage());
        }
        finally
        {
            db.endTransaction();
        }return  result;
    }
}
