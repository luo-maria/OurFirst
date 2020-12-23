package com.example.ourfirst.service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ourfirst.bean.User;
import com.example.ourfirst.util.DatabaseHelper;

public class UserService {
    private DatabaseHelper dbHelper;
    public UserService(Context context){
        dbHelper=new DatabaseHelper(context);
    }

    public boolean login(String username,String password){
        SQLiteDatabase sdb=dbHelper.getReadableDatabase();
        String sql="select * from user where username=? and password=?";
        Cursor cursor=sdb.rawQuery(sql, new String[]{username,password});
        if(cursor.moveToFirst()==true){
            cursor.close();
            return true;
        }
        sdb.close();
        return false;
    }
    public boolean register(User user){
        SQLiteDatabase sdb=dbHelper.getReadableDatabase();
        String sql="INSERT INTO user (username,password,number) VALUES(?,?,?)";
        Object obj[]={user.getUsername(),user.getPassword(),user.getNumber()};
        sdb.execSQL(sql, obj);
        return true;
    }
}
