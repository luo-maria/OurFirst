package com.example.ourfirst.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ourfirst.R;
import com.example.ourfirst.util.DatabaseHelper;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddActivity extends AppCompatActivity {

    private static final byte REQUEST_SYSTEM_PIC = 10;
    private DatabaseHelper dbHelper;
    private Spinner sp;
    private ImageView imageButton;
    private byte[] image;
    String leading_name,leading_call;
    //如何获取社团的id

    int club_id;
    String club_name;
    String club_create_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss ");
        //dbHelper=new MyDatabaseHelper(this,"1600802129.db",null,1);
        dbHelper = new DatabaseHelper(this);
        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        String[] ctype = new String[]{"财经特色类", "体育运动类", "艺术文化类", "志愿公益类"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ctype);  //创建一个数组适配器
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     //设置下拉列表框的下拉选项样式
        Spinner spinner = (Spinner) super.findViewById(R.id.activity_kind_sp);
        spinner.setAdapter(adapter);
        sp = (Spinner) findViewById(R.id.activity_kind_sp);
        final String kind = (String) sp.getSelectedItem();

        imageButton=(ImageView)findViewById(R.id.activity_logo);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(AddActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AddActivity.this, new
                            String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    //打开系统相册
                    Intent intent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 1);
                }
            }
        });

        Button create =(Button)findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Intent intent1=getIntent();
                club_id=Integer.valueOf(intent1.getStringExtra("id"));
                club_name=intent1.getStringExtra("club_name");
                club_create_id=intent1.getStringExtra("club_create_id");
                String username=intent1.getStringExtra("username");
                DatabaseHelper myhelper = new DatabaseHelper(AddActivity.this);
                SQLiteDatabase sdb=myhelper.getReadableDatabase();
                Cursor cursor = sdb.rawQuery("select * from club where club_name=?", new String[]{club_name});
                if (cursor != null && cursor.getColumnCount() > 0) {
                    while (cursor.moveToNext()) {
                        club_id = cursor.getInt(0);}
                    sdb.close();
                }

                ContentValues values=new ContentValues();
                EditText activityName=(EditText)findViewById(R.id.activity_name_et);
                EditText leadingCall=(EditText)findViewById(R.id.leading_call_et);
                EditText leadingName=(EditText)findViewById(R.id.leading_name);
                EditText activityIntro=(EditText)findViewById(R.id.activity_intro);
                Date curDate = new Date(System.currentTimeMillis());

                String activity_name = activityName.getText().toString();
                String leading_name = leadingName.getText().toString();
                String leading_call = leadingCall.getText().toString();
                String time = formatter.format(curDate);
                String activity_intro = activityIntro.getText().toString();
                System.out.println(activity_name);
                System.out.println(leading_name);
                System.out.println(leading_call);
                values.put("activity_name",activity_name);
                values.put("club_id",club_id);
                values.put("club_name",club_name);
                values.put("activity_images",image);
                values.put("activity_kind", kind);
                values.put("leading_name", leading_name);
                values.put("leading_call", leading_call);
                values.put("time", time);
                values.put("activity_intro",activity_intro);
                System.out.println(activity_intro);
                db.insert("activity",null,values);
                System.out.println(values);
                Toast.makeText(getApplicationContext(), "创建成功", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(AddActivity.this, HomeActivity.class);
                Intent intent3=getIntent();
                String username1=intent3.getStringExtra("username");
                intent.putExtra("username",username1);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取图片路径
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            showImage(imagePath);
            c.close();
        }
    }

    //加载图片
    private void showImage(String imaePath) {
        Bitmap bm = BitmapFactory.decodeFile(imaePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        image = baos.toByteArray();
        imageButton.setImageBitmap(bm);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
}
