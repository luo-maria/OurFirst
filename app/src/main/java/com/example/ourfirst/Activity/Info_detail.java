package com.example.ourfirst.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ourfirst.R;
import com.example.ourfirst.util.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Info_detail extends AppCompatActivity {
    TextView clubnames,info;
    Button pass,down;
    Intent intent;
    String resume_id;
    int club_id,stu_id;
    DatabaseHelper myhelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_detail);
        clubnames=findViewById(R.id.clubnames);
        info=findViewById(R.id.content_info);
        pass=findViewById(R.id.pass);
        down=findViewById(R.id.down);
        intent=getIntent();
        resume_id=intent.getStringExtra("id");
        myhelper = new DatabaseHelper(Info_detail.this);
        SQLiteDatabase db=myhelper.getReadableDatabase();
        Log.i("简历的id是",resume_id);
        Cursor cursor = db.rawQuery("select * from resume_info where resume_id=?",new String[]{resume_id}); // 根据接收到的id进行数据库查询
        if (cursor != null && cursor.getColumnCount() > 0) {
            while (cursor.moveToNext()) {
                info.setText(cursor.getString(5));
                club_id=cursor.getInt(1);
                stu_id=cursor.getInt(2);
                cursor.moveToNext();
            }
            db.close();
            }
        SQLiteDatabase db1=myhelper.getReadableDatabase();
        Cursor cursor1 = db1.rawQuery("select * from club where club_id=?",new String[]{String.valueOf(club_id)}); // 根据接收到的id进行数据库查询
        if (cursor1 != null && cursor1.getColumnCount() > 0) {
            while (cursor1.moveToNext()) {
                clubnames.setText(cursor1.getString(1));
                cursor1.moveToNext();
            }
            db1.close();
        }
        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper myhelper1 = new DatabaseHelper(Info_detail.this);
                SQLiteDatabase db1=myhelper1.getWritableDatabase();
                ContentValues values=new ContentValues();
                values.put("club_id",club_id);
                values.put("stu_id",stu_id);
                db1.insert("stu_club",null,values);
                db1.close();
                SQLiteDatabase sdb=myhelper.getWritableDatabase();
                sdb.execSQL("update resume_info set state=?where resume_id=?",
                        new Object[]{1,resume_id});
                sdb.close();
                final SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
                ContentValues values1=new ContentValues();
                Date curDate = new Date(System.currentTimeMillis());
                String back_time = formatter.format(curDate);
                System.out.println("这是申请时间："+back_time);
                values1.put("club_id",club_id);
                values1.put("stu_id",stu_id);
                values1.put("back_time",back_time);
                values1.put("state",1);
                SQLiteDatabase sdb1;
                sdb1=myhelper.getWritableDatabase();
                sdb1.insert("resume_info_back",null,values1);
                Toast.makeText(Info_detail.this,"审批成功！",Toast.LENGTH_SHORT).show();
                //刷新本页面
                Intent intent1=getIntent();
                String username=intent1.getStringExtra("username");
                Intent intent=new Intent(Info_detail.this, HomeActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
                finish();
            }

        });
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SimpleDateFormat formatter1 = new SimpleDateFormat("MM-dd");
                ContentValues values2=new ContentValues();
                Date curDate = new Date(System.currentTimeMillis());
                String back_time = formatter1.format(curDate);
                System.out.println("这是申请时间："+back_time);
                values2.put("club_id",club_id);
                values2.put("stu_id",stu_id);
                values2.put("back_time",back_time);
                values2.put("state",2);
                SQLiteDatabase sdb2;
                sdb2=myhelper.getWritableDatabase();
                sdb2.insert("resume_info_back",null,values2);
                sdb2.close();
                SQLiteDatabase sdb=myhelper.getWritableDatabase();
                sdb.execSQL("update resume_info set state=?where resume_id=?",
                        new Object[]{1,resume_id});
                sdb.close();
                Toast.makeText(Info_detail.this,"您已拒绝。",Toast.LENGTH_SHORT).show();
                Intent intent1=getIntent();
                String username=intent1.getStringExtra("username");
                Intent intent=new Intent(Info_detail.this, HomeActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
                finish();
            }
        });
    }
}
