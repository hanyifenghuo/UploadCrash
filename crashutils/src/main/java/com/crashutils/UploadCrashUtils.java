package com.crashutils;

import android.content.Context;

import com.crashutils.db.CrashBaen;
import com.crashutils.db.DaoHelp;
import com.j256.ormlite.dao.Dao;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.zxy.recovery.core.Recovery;
import com.zxy.recovery.exception.RecoveryException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by thinkpad on 2017/6/22.
 */

public class UploadCrashUtils {
    private String url;
    private String appName;
    private Context mContext;
    private volatile static UploadCrashUtils sInstance;
    private static final Object LOCK = new Object();
    private UploadCrashUtils() {
    }

    public static UploadCrashUtils getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = new UploadCrashUtils();
                }
            }
        }
        return sInstance;
    }
    public UploadCrashUtils init(Context mContext)
    {
        if (mContext == null)
            throw new RecoveryException("Context can not be null!");

        this.mContext=mContext;
        return this;
    }

    public List<CrashBaen> getCrashData()
    {
        List<CrashBaen> beanstemp;
        try {
            Dao<CrashBaen,Integer> dao= DaoHelp.getInstance(mContext).getDaoCrashBaen();
            beanstemp=dao.queryBuilder().where().eq("isUpLoad", 0).query();
        } catch (SQLException e) {
            e.printStackTrace();
            beanstemp=new ArrayList<>();
        }
        return beanstemp;
    }

    public void startAuto()
    {
        List<CrashBaen> beanstemp=getCrashData();
        for (final CrashBaen crashBaen:beanstemp)
        {
            if (url!=null&&!"".equals(url))
            {
                Hashtable<String, String> map=getParams(crashBaen);
                OkGo.post(url)
                        .tag(this)
                        .params(map)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                setOKupload(crashBaen);
                            }
                        });
            }

        }
    }

    public void start(Hashtable<String, String> map)
    {
        List<CrashBaen> beanstemp=getCrashData();
        for (final CrashBaen crashBaen:beanstemp)
        {
            if (url!=null&&!"".equals(url))
            {
                OkGo.post(url)
                        .tag(this)
                        .params(map)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                setOKupload(crashBaen);
                            }
                        });
            }
        }
    }

    //更新数据为已上传 0是未上传，1是已上传
    public void setOKupload(CrashBaen crashBaen)
    {
        try {
            crashBaen.setIsUpLoad(1);
            Dao<CrashBaen,Integer> dao= DaoHelp.getInstance(mContext).getDaoCrashBaen();
            dao.update(crashBaen);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Hashtable<String, String> getParams(CrashBaen crashBaen)
    {
        Hashtable<String, String> map = new Hashtable<>();
        map.put("Account", crashBaen.getAccount()==null?"":crashBaen.getAccount());
        map.put("AppVerisonCode", crashBaen.getAppVerisonCode()==null?"":crashBaen.getAppVerisonCode());
        map.put("AppVersionName", crashBaen.getAppVersionName()==null?"":crashBaen.getAppVersionName());
        map.put("CpuModel", crashBaen.getCpuModel()==null?"":crashBaen.getCpuModel());
        map.put("IMEI", crashBaen.getIMEI()==null?"":crashBaen.getIMEI());
        map.put("IMSI", crashBaen.getIMSI()==null?"":crashBaen.getIMSI());
        map.put("PhoneBrand", crashBaen.getPhoneBrand()==null?"":crashBaen.getPhoneBrand());
        map.put("PhoneModel", crashBaen.getPhoneModel()==null?"":crashBaen.getPhoneModel());
        map.put("PhoneNub", crashBaen.getPhoneNub()==null?"":crashBaen.getPhoneNub());
        map.put("PhoneType", crashBaen.getPhoneType()==null?"":crashBaen.getPhoneType());
        map.put("VersionRelease", crashBaen.getVersionRelease()==null?"":crashBaen.getVersionRelease());
        map.put("VersionSdk", crashBaen.getVersionSdk()==null?"":crashBaen.getVersionSdk());
        map.put("WidthAndHeight", crashBaen.getWidthAndHeight()==null?"":crashBaen.getWidthAndHeight());
        map.put("mStrException",crashBaen.getmStrException()==null?"":crashBaen.getmStrException());
        map.put("app_name", appName==null?"":appName);
        return  map;
    }

    public String getUrl() {
        return url;
    }

    public UploadCrashUtils setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getAppName() {
        return appName;
    }

    public UploadCrashUtils setAppName(String appName) {
        this.appName = appName;
        return this;
    }
}
