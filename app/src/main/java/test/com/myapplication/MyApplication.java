package test.com.myapplication;

import android.app.Application;
import android.util.Log;

import com.crashutils.CrachCallback;
import com.crashutils.UploadCrashUtils;
import com.zxy.recovery.core.Recovery;

/**
 * Created by thinkpad on 2017/6/21.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Recovery.getInstance()
                .debug(false)
                .recoverInBackground(false)
                .recoverStack(true)
                .mainPage(MainActivity.class)
                .recoverEnabled(true)
                .callback(new CrachCallback(this))
                .silent(true, Recovery.SilentMode.RECOVER_ACTIVITY_STACK)
                .init(this);

        UploadCrashUtils.getInstance().init(this).setUrl("http://192.168.2.116/ara/2/index/catchError")
        .setAppName(getString(R.string.app_name)).startAuto();
    }
    @Override
    public void onTerminate() {
        // 程序终止的时候执行
        UploadCrashUtils.getInstance().finishUpload();
        super.onTerminate();
    }
    @Override
    public void onLowMemory() {
        // 低内存的时候执行
        super.onLowMemory();
    }
    @Override
    public void onTrimMemory(int level) {
        // 程序在内存清理的时候执行
        super.onTrimMemory(level);
    }
}
