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
import com.example.ourfirst.fragment.HomePageFragment;
import com.example.ourfirst.util.DatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Apply_infoListActivity extends AppCompatActivity {
    Intent intent;
    byte[] imagedata;
    Bitmap imagebm;
    String username,stu_name;
    int stu_id,create_id,club_id;
    DatabaseHelper myhelper;
    Button myinfor,applyinfor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_info);
        ListView listView = (ListView)findViewById(R.id.list_info);
        Map<String, Object> item;
        final List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        Intent intent1=getIntent();
        username=intent1.getStringExtra("username");
        myhelper = new DatabaseHelper(Apply_infoListActivity.this);
        SQLiteDatabase sdb=myhelper.getReadableDatabase();
        Cursor cursor1 = sdb.rawQuery("select * from user where username=?", new String[]{username});
        if (cursor1 != null && cursor1.getColumnCount() > 0) {
            while (cursor1.moveToNext()) {
                create_id = cursor1.getInt(0);
                cursor1.close();
            }
            sdb.close();
        }
        SQLiteDatabase sdb2=myhelper.getReadableDatabase();
        Cursor cursor3 = sdb2.rawQuery("select * from club where club_create_id=?", new String[]{String.valueOf(create_id)});
        if (cursor3 != null && cursor3.getColumnCount() > 0) {
            while (cursor3.moveToNext()) {
                club_id = cursor3.getInt(0);
                cursor3.close();
            }
            sdb2.close();
        }
        
        item = new HashMap<String, Object>();  // 为列表项赋值
        SQLiteDatabase db=myhelper.getWritableDatabase();
        System.out.println("这是Apply_infoListActivity的club_id:"+club_id);
//        Cursor cursor = db.rawQuery("select * from club where club_id=?",new String[]{id1});
        Cursor cursor = db.rawQuery("select * from resume_info where club_id=?", new String[]{String.valueOf(club_id)});
        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                item.put("resume_id",cursor.getInt(0));
                item.put("club_id",cursor.getInt(1));
                item.put("stu_id",cursor.getString(2));
                item.put("apply_time",cursor.getString(3));
                item.put("stu_intro",cursor.getString(5));
                stu_id=cursor.getInt(2);
                cursor.moveToNext();

            }
            db.close();
        }
        SQLiteDatabase sdb1=myhelper.getReadableDatabase();
        Cursor cursor2 = sdb1.rawQuery("select * from user where student_id=?", new String[]{String.valueOf(stu_id)});
        if (cursor2 != null && cursor2.getColumnCount() > 0) {
            while (cursor2.moveToNext()) {
                stu_name = cursor2.getString(1);
                imagedata = cursor2.getBlob(7);
                imagebm = BitmapFactory.decodeByteArray(imagedata, 0, imagedata.length);
                cursor2.close();
            }
            sdb1.close();
        }
        item.put("stu_name",stu_name);
        item.put("image",imagebm);
        data.add(item); // 加入到列表中
        // 使用SimpleAdapter布局listview
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, data, R.layout.infoitem, new String[] { "image", "stu_name", "stu_intro", "apply_time"},
                new int[] { R.id.infoph, R.id.infoname, R.id.infotext, R.id.infotime });
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
                intent = new Intent(Apply_infoListActivity.this, Info_detail.class);
                intent.putExtra("id", data.get(position).get("resume_id").toString());
                Intent intent1=getIntent();
                String username=intent1.getStringExtra("username");
                intent.putExtra("username",username);
                String data2=data.get(position).get("resume_id").toString();
                System.out.println("这是data2:"+data2);
                // 获取该列表项的key为id的键值，即商品的id，将其储存在Bundle传递给打开的页面
                startActivity(intent);
            }
        });
        applyinfor=findViewById(R.id.applyinfo);
        applyinfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(Apply_infoListActivity.this, Apply_infoListActivity.class);
                Intent intent=getIntent();
                String username=intent.getStringExtra("username");
                intent3.putExtra("username",username);
                startActivity(intent3);
            }
        });
        myinfor=findViewById(R.id.myinfo);
        myinfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Apply_infoListActivity.this, Myinfo.class);
                Intent intent=getIntent();
                String username=intent.getStringExtra("username");
                intent2.putExtra("username",username);
                startActivity(intent2);
            }
        });
    }


}
