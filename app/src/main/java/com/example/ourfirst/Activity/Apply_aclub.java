package com.example.ourfirst.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ourfirst.R;
import com.example.ourfirst.util.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Apply_aclub extends AppCompatActivity {
    String username;
    String club_id;
    String editresumetext;
    String stu_name;
    String club_name;
    String club_create_id;
    int stu_id;
    DatabaseHelper myhelper;
    EditText editresume;
    SQLiteDatabase db,sdb,pdb;
    Button apply;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_aclub);
        Intent intent1=getIntent();
        club_id=intent1.getStringExtra("id");
        club_name=intent1.getStringExtra("club_name");
        club_create_id=intent1.getStringExtra("club_create_id");
        username=intent1.getStringExtra("username");
        System.out.println("这里是Apply_aclub的username:"+username);
        myhelper = new DatabaseHelper(Apply_aclub.this);
        db=myhelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from user where username=?", new String[]{username});
        if (cursor != null && cursor.getColumnCount() > 0) {
            while (cursor.moveToNext()) {
                stu_id = cursor.getInt(0);
                cursor.close();
            }
            db.close();}
//        pdb=myhelper.getWritableDatabase();
//        Cursor cursor1 = pdb.rawQuery("select * from club where club_name=?", new String[]{club_name});
//        if (cursor1 != null && cursor1.getColumnCount() > 0) {
//            while (cursor1.moveToNext()) {
//                club_portrait = cursor1.getBlob(4);
//                cursor1.close();
//            }
//            pdb.close();}

        apply=findViewById(R.id.apply);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入的内容保存到数据库的收入表中
                System.out.println("this is Apply_aclub_stu_id+++++++++="+stu_id);
                System.out.println("这里是Apply_aclub的club_id:"+club_id);
                editresume=findViewById(R.id.editresume);
                editresumetext=editresume.getText().toString();
                System.out.println("这里是Apply_aclub的 editresumetext:"+ editresumetext);
                final SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
                ContentValues values=new ContentValues();
                Date curDate = new Date(System.currentTimeMillis());
                String apply_time = formatter.format(curDate);
                System.out.println("这是申请时间："+apply_time);
                values.put("club_id",club_id);
                values.put("stu_id",stu_id);
                values.put("apply_time",apply_time);
                values.put("state",0);
                values.put("stu_intro",editresumetext);
                sdb=myhelper.getWritableDatabase();
                sdb.insert("resume_info",null,values);
                Toast.makeText(Apply_aclub.this,"申请成功",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Apply_aclub.this, HomeActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });

    }
}
