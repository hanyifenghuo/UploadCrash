package test.com.myapplication;

import android.app.Application;

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
}
