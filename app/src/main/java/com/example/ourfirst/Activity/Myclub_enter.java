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

public class Myclub_enter extends AppCompatActivity {
    String username;
    int stu_id,club_id;
    byte[] logo;
    Bitmap imagebm;
    DatabaseHelper myhelper;
    Button createclub,enterclub;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myclubs);
        Intent intent1=getIntent();
        ListView clubs = findViewById(R.id.myclubs);
        username=intent1.getStringExtra("username");
        myhelper=new DatabaseHelper(Myclub_enter.this);
        SQLiteDatabase db3=myhelper.getWritableDatabase();
        Cursor cursor3 = db3.rawQuery("select * from user where username=?",new String[]{username}); // 根据接收到的id进行数据库查询
        if (cursor3 != null && cursor3.getColumnCount() > 0) {
            while (cursor3.moveToNext()) {
                stu_id=cursor3.getInt(0);
                cursor3.moveToNext();
            }
            db3.close();
        }
        System.out.println("ajsajsjaj这里是Myclub_enter的stu_id:"+stu_id);
        SQLiteDatabase db5=myhelper.getWritableDatabase();
        Cursor cursor5 = db5.rawQuery("select * from stu_club where stu_id=?",new String[]{String.valueOf(stu_id)}); // 根据接收到的id进行数据库查询
        if (cursor5 != null && cursor5.getColumnCount() > 0) {
            while (cursor5.moveToNext()) {
                club_id=cursor5.getInt(0);
                cursor5.moveToNext();
            }
            db5.close();
        }
        System.out.println("ajsajsjaj这里是Myclub_enter的club_id:"+club_id);
        Map<String, Object> item;
        final List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        SQLiteDatabase db=myhelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from club where club_id=?",new String[]{String.valueOf(club_id)});
        if (cursor.moveToFirst()){
                item = new HashMap<String, Object>();  // 为列表项赋值
                while (!cursor.isAfterLast()){
                item.put("club_id",cursor.getInt(0));
                item.put("club_name",cursor.getString(1));
                item.put("club_create_id",cursor.getInt(2));
                item.put("level",cursor.getString(4));
                item.put("campus",cursor.getString(5));
                item.put("kind",cursor.getString(6));
                item.put("club_intro",cursor.getString(8));
                logo = cursor.getBlob(3);
                imagebm = BitmapFactory.decodeByteArray(logo, 0, logo.length);
                //kind1.setImageBitmap(imagebm);
                item.put("logo",imagebm);
                cursor.moveToNext();
                data.add(item); // 加入到列表中
            }

        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, data, R.layout.club_lists, new String[] { "logo", "club_name", "level", "campus", "kind" ,"club_intro"},
                new int[] { R.id.logo, R.id.clubname, R.id.clublevel, R.id.clubcampus, R.id.clubkind,R.id.clubintos  });
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
        clubs.setAdapter(simpleAdapter);
        // 为列表项设置监听器
        clubs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(Myclub_enter.this, Club_detailActivity.class);
                intent.putExtra("id", data.get(position).get("club_id").toString());
                // 获取该列表项的key为id的键值，即商品的id，将其储存在Bundle传递给打开的页面
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });

        createclub=findViewById(R.id.createclub);
        createclub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(Myclub_enter.this, MyclubsActivity.class);
                Intent intent=getIntent();
                String username=intent.getStringExtra("username");
                intent3.putExtra("username",username);
                startActivity(intent3);
            }
        });
        enterclub=findViewById(R.id.enterclub);
        enterclub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Myclub_enter.this,Myclub_enter.class);
                Intent intent=getIntent();
                String username=intent.getStringExtra("username");
                intent2.putExtra("username",username);
                startActivity(intent2);
            }
        });
    }
}
