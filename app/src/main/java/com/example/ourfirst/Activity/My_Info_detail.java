package com.example.ourfirst.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ourfirst.R;
import com.example.ourfirst.util.DatabaseHelper;

public class My_Info_detail extends AppCompatActivity {
    Intent intent;
    String resume_info_back_id, username;
    int club_id,stu_id;
    DatabaseHelper myhelper;
    int state;
    String stu_name,club_name;
    Button backback;
    TextView yourname,yourclubc,yourclub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__info_detail);
        intent=getIntent();
        username=intent.getStringExtra("username");
        myhelper = new DatabaseHelper(My_Info_detail.this);
        yourclub=findViewById(R.id.yourclub);
        yourclubc=findViewById(R.id.yourclubc);
        yourname=findViewById(R.id.yourname);
        SQLiteDatabase db3=myhelper.getWritableDatabase();
        Cursor cursor3 = db3.rawQuery("select * from user where username=?",new String[]{username}); // 根据接收到的id进行数据库查询
        if (cursor3 != null && cursor3.getColumnCount() > 0) {
            while (cursor3.moveToNext()) {
                stu_id=cursor3.getInt(0);
                cursor3.moveToNext();
            }
            db3.close();
        }
        SQLiteDatabase db=myhelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from resume_info_back where stu_id=?",new String[]{String.valueOf(stu_id)}); // 根据接收到的id进行数据库查询
        if (cursor != null && cursor.getColumnCount() > 0) {
            while (cursor.moveToNext()) {
                club_id=cursor.getInt(1);
                state=cursor.getInt(4);
                cursor.moveToNext();
            }
            db.close();
        }
        SQLiteDatabase db1=myhelper.getWritableDatabase();
        Cursor cursor1 = db1.rawQuery("select * from club where club_id=?",new String[]{String.valueOf(club_id)}); // 根据接收到的id进行数据库查询
        if (cursor1 != null && cursor1.getColumnCount() > 0) {
            while (cursor1.moveToNext()) {
                club_name=cursor1.getString(1);
                cursor1.moveToNext();
            }
            db1.close();
        }
//        SQLiteDatabase db2=myhelper.getWritableDatabase();
//        Cursor cursor2 = db2.rawQuery("select * from resume_info_back where resume_info_back_id=?",new String[]{resume_info_back_id}); // 根据接收到的id进行数据库查询
//        if (cursor2 != null && cursor2.getColumnCount() > 0) {
//            while (cursor2.moveToNext()) {
//                stu_name=cursor2.getString(1);
//                cursor2.moveToNext();
//            }
//            db2.close();
//        }
        Intent intent=getIntent();
        String username=intent.getStringExtra("username");
        if(state==1){
            yourclubc.setText("接受了");
        }else {
            yourclubc.setText("拒绝了");
        }
        yourname.setText(username);
        yourclub.setText(club_name);
        backback=findViewById(R.id.backback);
        backback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(My_Info_detail.this, HomeActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });

    }

}
