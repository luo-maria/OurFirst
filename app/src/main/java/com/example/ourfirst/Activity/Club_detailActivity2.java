package com.example.ourfirst.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ourfirst.R;
import com.example.ourfirst.util.DatabaseHelper;

public class Club_detailActivity2 extends AppCompatActivity {
    byte[] imagedata;
    Bitmap imagebm;
    Button app_btn;
    String id1,club_name;
    int club_create_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_detail2);
        final DatabaseHelper dbtest = new DatabaseHelper(this);
        final SQLiteDatabase db = dbtest.getWritableDatabase();
        ImageView logo = (ImageView)findViewById(R.id.logophoto);
        TextView level = (TextView)findViewById(R.id.level1);
        TextView campus = (TextView)findViewById(R.id.campus1) ;
        TextView kind = (TextView)findViewById(R.id.kind1);
        TextView clubname = (TextView)findViewById(R.id.club_name);
        TextView time = (TextView)findViewById(R.id.time);
        TextView createname = (TextView)findViewById(R.id.createname);
        TextView club_intro = (TextView)findViewById(R.id.club_intro1);
        TextView call = (TextView)findViewById(R.id.call);
        app_btn = (Button) findViewById(R.id.createActivity);
        Intent intent1=getIntent();
        id1=intent1.getStringExtra("id");
        String username=intent1.getStringExtra("username");
        System.out.println("这里是Club_detailActivity2的username:"+username);
        Log.i("商品的id是",intent1.getStringExtra("id"));
//        Cursor cursor = sdb.rawQuery("select * from user where username=?", new String[]{username});
        Cursor cursor = db.rawQuery("select * from club where club_id=?",new String[]{id1}); // 根据接收到的id进行数据库查询
        if (cursor != null && cursor.getColumnCount() > 0) {
            while (cursor.moveToNext()) {
                imagedata = cursor.getBlob(3);
                imagebm = BitmapFactory.decodeByteArray(imagedata, 0, imagedata.length);
                logo.setImageBitmap(imagebm);
                clubname.setText(cursor.getString(1));
                level.setText(cursor.getString(4));
                campus.setText(cursor.getString(5));
                kind.setText(cursor.getString(6));
                time.setText(cursor.getString(9));
                createname.setText(cursor.getString(7));
                club_intro.setText(cursor.getString(8));
                call.setText(cursor.getString(10));
                club_create_id = cursor.getInt(2);
                club_name=cursor.getString(1);
                cursor.moveToNext();
            }
        }
    app_btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Club_detailActivity2.this, AddActivity.class);
//            Intent intent3=getIntent();
//            String username=intent3.getStringExtra("username");
//            intent.putExtra("username",username);
            intent.putExtra("id",id1);
            System.out.println("这里是Club_detailActivity2的club_create_id:"+club_create_id);
            intent.putExtra("club_create_id",String.valueOf(club_create_id));
            intent.putExtra("club_name",club_name);
            intent.putExtra("username",username);
            startActivity(intent);
        }
    });}


}

