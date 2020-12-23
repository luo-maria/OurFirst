package com.example.ourfirst.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    static String name="myuser.db";
    static int dbVersion=1;
    public DatabaseHelper(Context context) {
        super(context, name, null, dbVersion);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS user(" +
                "student_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT," +
                "number TEXT," +
                "password TEXT," +
                "student_class varchar(20)," +
                "signature varchar(200)," +
                "realname TEXT," +
                "portrait blob," +
                "gender varchar(20))"
        );
//        社团表
        db.execSQL("CREATE TABLE IF NOT EXISTS club(" +
                "club_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "club_name varchar(20)," +
                "club_create_id INTEGER," +
                "logo blob," +
                "level varchar(20)," +
                "campus varchar(20)," +
                "kind varchar(20)," +
                "leader_name varchar(20)," +
                "club_intro varchar(500)," +
                "time DATETIME," +
                "leader_call varchar(20))");
//        学生社团表
        db.execSQL("CREATE TABLE IF NOT EXISTS stu_club(" +
                "stu_club_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "club_id INTEGER," +
                "stu_id INTEGER)");
        //        简历信息表
        db.execSQL("CREATE TABLE IF NOT EXISTS resume_info(" +
                "resume_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "club_id INTEGER," +
                "stu_id INTEGER," +
                "apply_time DATETIME," +
                "state INTEGER," +
                "stu_intro varchar(500))");
//        审批返回信息
        db.execSQL("CREATE TABLE IF NOT EXISTS resume_info_back(" +
                "resume_info_back_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "club_id INTEGER," +
                "stu_id INTEGER," +
                "back_time DATETIME," +
                "state INTEGER)");
        //        活动表  “活动id，社团id，活动宣传图，活动名称，活动种类，负责人姓名，负责人电话，发布时间，活动介绍
        db.execSQL("CREATE TABLE IF NOT EXISTS activity(" +
                "activity_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "club_id INTEGER," +
                "club_name  varchar(20)," +
                "activity_images blob," +
                "activity_name varchar(20)," +
                "activity_kind varchar(20)," +
                "leading_name varchar(20)," +
                "leading_call varchar(20)," +
                "time DATETIME," +
                "activity_intro varchar(500))");
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

