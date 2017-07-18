# UploadCrash
上传崩溃日志，基于Recovery上调整


## 使用

```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
 ```
 
 [![](https://jitpack.io/v/hanyifenghuo/UploadCrash.svg)](https://jitpack.io/#hanyifenghuo/UploadCrash)
 ```
 dependencies {
	        compile 'com.github.hanyifenghuo.UploadCrash:crashutils:xx.xx'
	}
  ```

```
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


        UploadCrashUtils.getInstance().init(this).setUrl("http://192.168.2.116/ara/2/index/catchError")
        .setAppName(getString(R.string.app_name)).startAuto();
    }
        @Override
    public void onTerminate() {
        // 程序终止的时候执行
        UploadCrashUtils.getInstance().finishUpload();
        super.onTerminate();
     }
}
```

## 如果需要账号保存，登录后调用下面的代码，并传入用户
```
CrachCallback crachCallback=(CrachCallback)(Recovery.getInstance().getmCallback());
crachCallback.setAccount("假装客户");
```
