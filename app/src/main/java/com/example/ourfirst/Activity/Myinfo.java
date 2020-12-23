package com.example.ourfirst.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.ourfirst.R;
import com.example.ourfirst.util.DatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Myinfo extends AppCompatActivity {
    Intent intent;
    byte[] imagedata;
    Bitmap imagebm;
    String username,club_name,back_time;
    int stu_id,club_id,resume_info_back_id;
    DatabaseHelper myhelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_info);
        ListView listView = (ListView)findViewById(R.id.list_info);
        Map<String, Object> item;
        final List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        Intent intent1=getIntent();
        username=intent1.getStringExtra("username");
        myhelper = new DatabaseHelper(Myinfo.this);
        SQLiteDatabase sdb=myhelper.getReadableDatabase();
        Cursor cursor1 = sdb.rawQuery("select * from user where username=?", new String[]{username});
        if (cursor1 != null && cursor1.getColumnCount() > 0) {
            while (cursor1.moveToNext()) {
                stu_id = cursor1.getInt(0);
                cursor1.close();
            }
            sdb.close();
        }
        System.out.println("SSSsafawgerghbfhssssss"+stu_id);
        SQLiteDatabase sdb2=myhelper.getWritableDatabase();
        Cursor cursor3 = sdb2.rawQuery("select * from resume_info_back where stu_id=?", new String[]{String.valueOf(stu_id)});
        if (cursor3 != null && cursor3.getColumnCount() > 0) {
            while (cursor3.moveToNext()) {
                resume_info_back_id = cursor3.getInt(0);
                club_id = cursor3.getInt(1);
                back_time=cursor3.getString(3);
                cursor3.close();
            }
            sdb2.close();
        }
        System.out.println("SSSssssssssssssss"+club_id+resume_info_back_id+back_time);
        SQLiteDatabase sdb3=myhelper.getWritableDatabase();
        Cursor cursor4 = sdb3.rawQuery("select * from club where club_id=?", new String[]{String.valueOf(club_id)});
        if (cursor4 != null && cursor4.getColumnCount() > 0) {
            while (cursor4.moveToNext()) {
                club_id = cursor4.getInt(1);
                club_name= cursor4.getString(1);
                imagedata = cursor4.getBlob(3);
                imagebm = BitmapFactory.decodeByteArray(imagedata, 0, imagedata.length);
                cursor4.close();
            }
            sdb3.close();
        }
        item = new HashMap<String, Object>();  // 为列表项赋值
        item.put("image",imagebm);
        item.put("resume_info_back_id", resume_info_back_id);
        item.put("club_name", club_name);
        item.put("myinfo", "审批信息");
        item.put("back_time",back_time);
        data.add(item); // 加入到列表中
        System.out.println(imagebm+club_name+back_time);
        SQLiteDatabase db=myhelper.getWritableDatabase();
        System.out.println("这是Apply_infoListActivity的resume_info_back_id:"+resume_info_back_id);
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, data, R.layout.myinfoitem, new String[] { "image", "club_name", "myinfo","back_time"},
                new int[] { R.id.infoph1,R.id.infotext, R.id.infoname1,R.id.infotime1 });
        simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data, String textRepresentation) {
                if(view instanceof ImageView && data instanceof Bitmap){
                    ImageView iv = (ImageView)view;
                    iv.setImageBitmap( (Bitmap)data );
                    return true;
                }else{
                    return false;
                }
            }
        });
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(Myinfo.this, My_Info_detail.class);
                intent.putExtra("id", data.get(position).get("resume_info_back_id").toString());
                Intent intent1=getIntent();
                String username=intent1.getStringExtra("username");
                intent.putExtra("username",username);
                String data3=data.get(position).get("resume_info_back_id").toString();
                System.out.println("这是data3:"+data3);
                // 获取该列表项的key为id的键值，即商品的id，将其储存在Bundle传递给打开的页面
                startActivity(intent);
            }
        });

    }

}
