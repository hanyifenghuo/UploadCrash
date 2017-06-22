package test.com.myapplication;

import android.app.Application;

import com.crashutils.CrachCallback;
import com.crashutils.UploadCrashUtils;
import com.zxy.recovery.core.Recovery;

/**
 * Created by thinkpad on 2017/6/21.
 */

public class MyApplication extends Application {
    CrachCallback crachCallback;
    @Override
    public void onCreate() {
        super.onCreate();
         crachCallback=new CrachCallback(this);
        Recovery.getInstance()
                .debug(true)
                .recoverInBackground(false)
                .recoverStack(true)
                .mainPage(MainActivity.class)
                .recoverEnabled(true)
                .callback(crachCallback)
                .silent(true, Recovery.SilentMode.RECOVER_ACTIVITY_STACK)
                .init(this);
        UploadCrashUtils.getInstance().init(this).setUrl("http://192.168.2.116/ara/2/index/catchError")
        .setAppName("测试的APP").startAuto();

    }

    public CrachCallback getCrachCallback() {
        return crachCallback;
    }

    public void setCrachCallback(CrachCallback crachCallback) {
        this.crachCallback = crachCallback;
    }
}
