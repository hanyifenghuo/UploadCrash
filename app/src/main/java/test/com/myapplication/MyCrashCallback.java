package test.com.myapplication;

import android.util.Log;

import com.zxy.recovery.callback.RecoveryCallback;

/**
 * Created by thinkpad on 2017/6/21.
 */

public class MyCrashCallback implements RecoveryCallback {
    @Override
    public void stackTrace(String stackTrace) {
        Log.e("崩溃回调","stackTrace");
    }

    @Override
    public void cause(String cause) {
        Log.e("崩溃回调","cause"+"---->"+cause);
    }

    @Override
    public void exception(String throwExceptionType, String throwClassName, String throwMethodName, int throwLineNumber) {
        Log.e("崩溃回调","exception"+"\n---->"+throwExceptionType
        +"\n---->"+throwClassName
        +"\n---->"+throwMethodName+
                "\n---->"+throwLineNumber);
    }

    @Override
    public void throwable(Throwable throwable) {
        Log.e("崩溃回调","throwable"+"---->"+throwable.toString());
    }
}
