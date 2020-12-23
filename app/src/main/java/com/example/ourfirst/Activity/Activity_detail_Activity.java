package com.example.ourfirst.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ourfirst.R;
import com.example.ourfirst.util.DatabaseHelper;

public class Activity_detail_Activity  extends AppCompatActivity {


    byte[] imagedata;
    Bitmap imagebm;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_detail);
        final DatabaseHelper dbtest = new DatabaseHelper(this);
        final SQLiteDatabase db = dbtest.getWritableDatabase();
        ImageView image = findViewById(R.id.image);
        TextView activity_name =  findViewById(R.id.activity_name);
        TextView activity_create_name = findViewById(R.id.activity_create_name);
        TextView leading_name =  findViewById(R.id.leading_name);
        TextView leading_call =   findViewById(R.id.leading_call);
        TextView activity_intro =  findViewById(R.id.activity_intro);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        String username = intent.getStringExtra("username");
        System.out.println("这里是首页首页首页首页首页！！的username:" + username);
        Log.i("商品的id是", intent.getStringExtra("id"));


        Cursor cursor = db.rawQuery("select * from activity where activity_id=?", new String[]{id}); // 根据接收到的id进行数据库查询
        if (cursor != null && cursor.getColumnCount() > 0) {
            while (cursor.moveToNext()) {
                imagedata = cursor.getBlob(3);
                imagebm = BitmapFactory.decodeByteArray(imagedata, 0, imagedata.length);
                image.setImageBitmap(imagebm);
                activity_name.setText(cursor.getString(4));
                activity_create_name.setText(cursor.getString(2));
                leading_name.setText(cursor.getString(6));
                leading_call.setText(cursor.getString(7));
                activity_intro.setText(cursor.getString(9));

                cursor.moveToNext();
            }
        }
//        app_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Activity_detail_Activity.this, AddActivity.class);
//                intent.putExtra("id",id1);
//                System.out.println("这里是Club_detailActivity的club_create_id:"+club_create_id);
//                intent.putExtra("club_create_id",String.valueOf(club_create_id));
//                intent.putExtra("club_name",club_name);
//                intent.putExtra("username",username);
//                startActivity(intent);
//            }
//        });}

    }
}

