package com.xiaojiang.hotfix;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.xiaojiang.hotfix.fixutils.FixUtil;

/**
 * Created by guoxiaojiang on 17/3/2.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        FixUtil.loadFixedDex(base);
    }
}
