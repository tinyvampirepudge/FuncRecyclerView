package com.tinytongtong.funcrecyclerview;

import android.app.Application;

import com.coder.zzq.smartshow.core.SmartShow;

/**
 * @Description: TODO
 * @Author wangjianzhou@qding.me
 * @Date 2020/5/24 7:02 PM
 * @Version TODO
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SmartShow.init(this);
    }
}
