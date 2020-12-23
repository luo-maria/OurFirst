package com.example.ourfirst.Activity;

import android.content.IntentFilter;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ourfirst.util.ActivityCollector;
import com.example.ourfirst.util.LoginOutBroadcastReceiver;

public class BaseCompatActivity extends AppCompatActivity {
    protected LoginOutBroadcastReceiver locallReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
}

