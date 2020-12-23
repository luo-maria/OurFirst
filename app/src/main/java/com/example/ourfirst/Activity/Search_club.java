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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import com.example.ourfirst.R;
import com.example.ourfirst.fragment.OrganizationFragment;
import com.example.ourfirst.util.DatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Search_club extends AppCompatActivity {
    Spinner sp, sp1, sp2;
    String kind, level, campus,kind1,level1,campus1,search_text;
    String data1,data2,data3;
    ArrayList arr1 = new ArrayList();
    Intent intent;
    byte[] logo;
    Bitmap imagebm;
    ImageView arrow,search;
    EditText et_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_organization);
        sp = findViewById(R.id.level2);
        sp1 = findViewById(R.id.campus2);
        sp2 = findViewById(R.id.kind2);
        arrow=findViewById(R.id.arrow);
        ListView clubs = findViewById(R.id.clubs);
        Intent intent1=getIntent();
        String username=intent1.getStringExtra("username");
        search_text=intent1.getStringExtra("search_text");
        System.out.println("这里是search_text:"+search_text);
        DatabaseHelper dbtest = new DatabaseHelper(Search_club.this);
        final SQLiteDatabase db = dbtest.getWritableDatabase();

        // 为列表项设置监听器
        Map<String, Object> item;
        final List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        Cursor cursor = db.rawQuery("select * from club where club_name like ? ",new String[]{"%"+search_text+"%"});
        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                item = new HashMap<String, Object>();  // 为列表项赋值
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
        String[] ltype = new String[]{"校级", "院级"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ltype);  //创建一个数组适配器
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     //设置下拉列表框的下拉选项样式
        sp.setAdapter(adapter);
        level = (String) sp.getSelectedItem();
        String[] ctype = new String[]{"燕山校区", "圣井校区", "舜耕校区", "莱芜校区"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ctype);  //创建一个数组适配器
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     //设置下拉列表框的下拉选项样式
        sp1.setAdapter(adapter1);
        campus = (String) sp1.getSelectedItem();
        System.out.println("campus:"+campus);
        String[] ktype = new String[]{"公益", "文艺", "学术", "文体", "其他"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ktype);  //创建一个数组适配器
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     //设置下拉列表框的下拉选项样式
        sp2.setAdapter(adapter2);
        kind = (String) sp2.getSelectedItem();
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
                intent = new Intent(Search_club.this, Club_detailActivity.class);
                intent.putExtra("id", data.get(position).get("club_id").toString());
                // 获取该列表项的key为id的键值，即商品的id，将其储存在Bundle传递给打开的页面
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });
        search=findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_text=et_search.getText().toString();
                Intent intent = new Intent(Search_club.this, Search_club.class);
                Intent intent1=getIntent();
                String username=intent1.getStringExtra("username");
                intent.putExtra("username",username);
                intent.putExtra("search_text",search_text);
                startActivity(intent);
            }
        });
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
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.setAdapter(adapter);
                sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView1, View view,
                                               int position, long id) {
                        //获取选中值
                        Spinner spinner1 = (Spinner) adapterView1;
                        data1=spinner1.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }
                });
                System.out.println("这里的data11是:"+data1);
                sp1.setAdapter(adapter1);
                sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView2, View view,
                                               int position, long id) {
                        //获取选中值
                        Spinner spinner2 = (Spinner) adapterView2;
                        data2=spinner2.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }
                });
                System.out.println("这里的data22是:"+data2);

                sp2.setAdapter(adapter2);
                sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView3, View view,
                                               int position, long id) {
                        //获取选中值
                        Spinner spinner3 = (Spinner) adapterView3;
                        data3=spinner3.getItemAtPosition(position).toString();
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }
                });
                System.out.println("这里的data33是:"+data3);
                Intent intent = new Intent(Search_club.this, Select_club.class);
                Intent intent1=getIntent();
                String username=intent1.getStringExtra("username");
                intent.putExtra("username",username);
                intent.putExtra("level",data1);
                intent.putExtra("campus",data2);
                intent.putExtra("kind",data3);
                startActivity(intent);
            }
        });
    }
}
