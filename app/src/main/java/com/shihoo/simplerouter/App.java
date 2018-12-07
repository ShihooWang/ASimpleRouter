package com.shihoo.simplerouter;

import android.app.Application;

import com.shihoo.router_library.ARouter;

/**
 * Created by shihoo ON 2018/12/7.
 * Email shihu.wang@bodyplus.cc 451082005@qq.com
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ARouter.init(this);
    }
}
