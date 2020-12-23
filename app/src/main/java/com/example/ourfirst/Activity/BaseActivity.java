package com.example.ourfirst.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ourfirst.util.ActivityCollector;
import com.example.ourfirst.util.LoginOutBroadcastReceiver;
import com.youth.banner.util.LogUtils;

public abstract class BaseActivity extends AppCompatActivity {
    public Context mContext;
    protected LoginOutBroadcastReceiver locallReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(initLayout());
        initView();
        initData();
        // 创建活动时，将其加入管理器中
        ActivityCollector.addActivity(this);
    }



    @Override
    protected void onResume() {
        super.onResume();

        // 注册广播接收器
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.gesoft.admin.loginout");
        locallReceiver = new LoginOutBroadcastReceiver();
        registerReceiver(locallReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // 取消注册广播接收器
        unregisterReceiver(locallReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 销毁活动时，将其从管理器中移除
        ActivityCollector.removeActivity(this);
    }


    protected abstract int initLayout();

    protected abstract void initView();

    protected abstract void initData();

    //对Toast提示方法进行封装！
    public void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    public void showToastSync(String msg) {
        Looper.prepare();
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        Looper.loop();
    }

    //跳转的方法
    public void navigateTo(Class cls) {
        Intent in = new Intent(mContext, cls);
        startActivity(in);
    }

    public void navigateToWithFlag(Class cls, int flags) {
        Intent in = new Intent(mContext, cls);
        in.setFlags(flags);
        startActivity(in);
    }

    protected void insertVal(String key, String val) {
        SharedPreferences sp = getSharedPreferences("sp_ttit", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, val);
        editor.commit();
    }

    protected String findByKey(String key) {
        SharedPreferences sp = getSharedPreferences("sp_ttit", MODE_PRIVATE);
        return sp.getString(key, "");

    }

}
