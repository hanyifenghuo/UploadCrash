# UploadCrash
上传崩溃日志，基于Recovery上调整

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}


dependencies {
	        compile 'com.github.hanyifenghuo.UploadCrash:crashutils:v1.0'
	}

//startAuto封装了参数

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Recovery.getInstance()
                .debug(true)
                .recoverInBackground(false)
                .recoverStack(true)
                .mainPage(MainActivity.class)
                .recoverEnabled(true)
                .callback(new CrachCallback(this))
                .silent(true, Recovery.SilentMode.RECOVER_ACTIVITY_STACK)
                .init(this);


        UploadCrashUtils.getInstance().init(this).setUrl("url_crash")
        .setAppName(getString(R.string.app_name)).startAuto();
    }
}

登录后需要上传账号的，需要调用

CrachCallback crachCallback=(CrachCallback)(Recovery.getInstance().getmCallback());
crachCallback.setAccount("假装客户");
