package com.example.ourfirst.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ourfirst.R;
import com.example.ourfirst.bean.User;
import com.example.ourfirst.service.UserService;
import com.example.ourfirst.util.DatabaseHelper;

public class EditUser extends AppCompatActivity {
    private EditText stuusernametxt1, stupasstxt, stunametxt, stunumtxt, stuclasstxt, stusigtxt;
    private TextView savetxt,cancel;
    private RadioGroup rg;
    RadioButton radioButton;
    String reusername, repassword, rerealname, regender, renumber, reclass, resig,username1,number1,password1,student_calss1,realname1,gender1,signature1,username;
    int stu_id;
    private SQLiteDatabase db;
    DatabaseHelper myhelper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_information);
        Intent intent=getIntent();
        username=intent.getStringExtra("username");
//        String username="thank";
        System.out.println("edit+++++####"+username);
        myhelper = new DatabaseHelper(EditUser.this);
        SQLiteDatabase sdb=myhelper.getReadableDatabase();
        Cursor cursor = sdb.rawQuery("select * from user where username=?", new String[]{username});
        if (cursor != null && cursor.getColumnCount() > 0) {
            while (cursor.moveToNext()) {
                stu_id = cursor.getInt(0);
                username1 = cursor.getString(1);
                number1 = cursor.getString(2);
                password1=cursor.getString(3);
                student_calss1=cursor.getString(4);
                signature1=cursor.getString(5);
                realname1=cursor.getString(6);
                gender1=cursor.getString(8);
                cursor.close();
            }
            sdb.close();}
        System.out.println("this is id+++++++++="+stu_id);

        stunametxt=findViewById(R.id.stunametxt);
        stupasstxt=findViewById(R.id.stupasstxt);
        stuusernametxt1=findViewById(R.id.stuusernametxt);
        stunumtxt=findViewById(R.id.stunumtxt);
        stuclasstxt=findViewById(R.id.stuclasstxt);
        stusigtxt=findViewById(R.id.stusigtxt);
        rg= findViewById(R.id.rg);
        stuusernametxt1.setText(username1);
        stupasstxt.setText(password1);
        stunumtxt.setText(number1);
        stunametxt.setText(realname1);
        stuclasstxt.setText(student_calss1);
        stusigtxt.setText(signature1);
        savetxt=findViewById(R.id.savetxt);
        cancel=findViewById(R.id.cancel);
                rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selectRadioBtn();
            }
        });
                myhelper = new DatabaseHelper(EditUser.this);

        db = myhelper.getWritableDatabase();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditUser.this, HomeActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });

        savetxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reusername=stuusernametxt1.getText().toString();
                rerealname=stunametxt.getText().toString();
                renumber=stunumtxt.getText().toString();
                resig=stusigtxt.getText().toString();
                reclass=stuclasstxt.getText().toString();
                repassword=stupasstxt.getText().toString();
                System.out.println("hsssssssssssssssssssssssssssssssreusername"+reusername);
                db.execSQL("update user set username=?,password=?,number=?,signature=?,student_class=?,gender=?,realname=?where student_id=?",
                        new Object[]{reusername,repassword,renumber,resig,reclass,regender,rerealname,stu_id});
                db.close();
                Toast.makeText(EditUser.this,"修改成功",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditUser.this, HomeActivity.class);
                intent.putExtra("username",reusername);
                startActivity(intent);
            }
        });
    }
        private void selectRadioBtn(){
        radioButton = findViewById(rg.getCheckedRadioButtonId());
        regender = radioButton.getText().toString();
    }

}