package com.example.ourfirst.Activity;

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
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.ourfirst.R;
import com.example.ourfirst.fragment.HomePageFragment;
import com.example.ourfirst.util.DatabaseHelper;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateClubActivity extends AppCompatActivity {

    Button btncreate;
    EditText et_club_name,et_leader_name,et_leader_call,et_club_intro;
    DatabaseHelper mhelper;
    SQLiteDatabase db;
    Spinner sp,sp1,sp2;
    String data1,data2,data3,data11,data22,data33;
    ImageView clublogo,cover;
    int stu_id;
    private byte[] image1;
    String kind,level,campus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_createclub);
        //2 绑定控件
        et_club_name=findViewById(R.id.clubname);
        et_leader_name=findViewById(R.id.leadername);
        et_leader_call=findViewById(R.id.leadercall);
        et_club_intro=findViewById(R.id.clubintro);
        clublogo=findViewById(R.id.clublogo);
        btncreate=findViewById(R.id.create);
        mhelper=new DatabaseHelper(CreateClubActivity.this);
        db=mhelper.getWritableDatabase();
        clublogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(CreateClubActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(CreateClubActivity.this, new
                            String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    //打开系统相册
                    Intent intent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 1);
                }
            }
        });


        String[] ltype = new String[]{"校级", "院级"};
        sp = (Spinner) super.findViewById(R.id.level);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ltype);  //创建一个数组适配器
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     //设置下拉列表框的下拉选项样式
        sp.setAdapter(adapter);
//        sp = (Spinner) findViewById(R.id.level);
        level = (String) sp.getSelectedItem();
        System.out.println("这里是级别："+level);
        String[] ctype = new String[]{"燕山校区", "圣井校区","舜耕校区","莱芜校区"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ctype);  //创建一个数组适配器
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     //设置下拉列表框的下拉选项样式
        Spinner spinner1 = (Spinner) super.findViewById(R.id.campus);
        spinner1.setAdapter(adapter1);
        sp1 = (Spinner) findViewById(R.id.campus);
        campus = (String) sp1.getSelectedItem();
        System.out.println("这里是校区："+campus);
        String[] ktype = new String[]{"公益", "文艺","学术","文体","其他"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ktype);  //创建一个数组适配器
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     //设置下拉列表框的下拉选项样式
        Spinner spinner2 = (Spinner) super.findViewById(R.id.kind);
        spinner2.setAdapter(adapter2);
        sp2 = (Spinner) findViewById(R.id.kind);
        kind= (String) sp2.getSelectedItem();
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                //获取选中值
                Spinner spinner1 = (Spinner) adapterView;
                data1=spinner1.getItemAtPosition(position).toString();
                System.out.println("这里的data1是:"+data1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                //获取选中值
                Spinner spinner2 = (Spinner) adapterView;
                data2=spinner2.getItemAtPosition(position).toString();
                System.out.println("这里的data2是:"+data2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                //获取选中值
                Spinner spinner3 = (Spinner) adapterView;
                data3=spinner3.getItemAtPosition(position).toString();
                System.out.println("这里的data3是:"+data3);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        //3保存按钮功能的实现
        btncreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp = findViewById(R.id.level);
                sp.setAdapter(adapter);
                sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view,
                                               int position, long id) {
                        //获取选中值
                        Spinner spinner1 = (Spinner) adapterView;
                        data1=spinner1.getItemAtPosition(position).toString();
                        System.out.println("这里的data11是:"+data1);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }
                });
                System.out.println("这里的data11是:"+data1);
                sp1 = findViewById(R.id.campus);
                sp1.setAdapter(adapter);
                sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view,
                                               int position, long id) {
                        //获取选中值
                        Spinner spinner2 = (Spinner) adapterView;
                        data2=spinner2.getItemAtPosition(position).toString();
                        System.out.println("这里的data22是:"+data2);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }
                });
                System.out.println("这里的data22是:"+data2);
                sp2 = findViewById(R.id.kind);
                sp2.setAdapter(adapter);
                sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view,
                                               int position, long id) {
                        //获取选中值
                        Spinner spinner3 = (Spinner) adapterView;
                        data3=spinner3.getItemAtPosition(position).toString();
                        System.out.println("这里的data33是:"+data3);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }
                });
                System.out.println("这里的data33是:"+data3);
                final SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
                Intent intent1=getIntent();
                String username=intent1.getStringExtra("username");
                DatabaseHelper myhelper = new DatabaseHelper(CreateClubActivity.this);
                SQLiteDatabase sdb=myhelper.getReadableDatabase();
                Cursor cursor = sdb.rawQuery("select * from user where username=?", new String[]{username});
                if (cursor != null && cursor.getColumnCount() > 0) {
                    while (cursor.moveToNext()) {
                        stu_id = cursor.getInt(0);}
                    sdb.close();}

                //获取输入的内容保存到数据库的收入表中
                ContentValues values=new ContentValues();
                Date curDate = new Date(System.currentTimeMillis());
                String time = formatter.format(curDate);
                System.out.println("这是创建时间："+time);
                values.put("club_name",et_club_name.getText().toString());
                values.put("club_create_id",stu_id);
                values.put("logo",image1);
                values.put("level",data1);
                values.put("campus",data2);
                values.put("kind",data3);
                values.put("leader_name",et_leader_name.getText().toString());
                values.put("leader_call",et_leader_call.getText().toString());
                values.put("time",time);
                values.put("club_intro",et_club_intro.getText().toString());
                db.insert("club",null,values);
                Toast.makeText(CreateClubActivity.this,"创建成功",Toast.LENGTH_SHORT).show();
                //刷新本页面
                Intent intent=new Intent(CreateClubActivity.this, HomeActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
                finish();
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
        image1 = baos.toByteArray();
        clublogo.setImageBitmap(bm);
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