package com.crashutils.db;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.crashutils.PhoneUtils;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by thinkpad on 2017/4/24.
 */
@DatabaseTable(tableName = "tb_CrashBaen")          //创建的表名
public class CrashBaen {

    @DatabaseField(generatedId = true)             //id为主键且自动生成
    private int id;

    @DatabaseField(columnName = "Account")
    private String Account;

    @DatabaseField(columnName = "AppVerisonCode")
    private String AppVerisonCode;

    @DatabaseField(columnName = "AppVersionName")
    private String AppVersionName;

    @DatabaseField(columnName = "CpuModel")
    private String CpuModel;

    @DatabaseField(columnName = "IMEI")
    private String IMEI;

    @DatabaseField(columnName = "IMSI")
    private String IMSI;


    @DatabaseField(columnName = "PhoneBrand")
    private String PhoneBrand;


    @DatabaseField(columnName = "PhoneModel")
    private String PhoneModel;

    @DatabaseField(columnName = "PhoneNub")
    private String PhoneNub;

    @DatabaseField(columnName = "PhoneType")
    private String PhoneType;

    @DatabaseField(columnName = "VersionRelease")
    private String VersionRelease;

    @DatabaseField(columnName = "WidthAndHeight")
    private String WidthAndHeight;

    @DatabaseField(columnName = "VersionSdk")
    private String VersionSdk;

    @DatabaseField(columnName = "mStrException")
    private String mStrException;


    @DatabaseField(columnName = "CreatTime")
    private String CreatTime;

    //0是未上传，1是已上传
    @DatabaseField(columnName = "isUpLoad" )
    private int isUpLoad;
    public CrashBaen(){}

    public int getIsUpLoad() {
        return isUpLoad;
    }

    public void setIsUpLoad(int isUpLoad) {
        this.isUpLoad = isUpLoad;
    }

    public String getCreatTime() {
        return CreatTime;
    }

    public void setCreatTime(String creatTime) {
        CreatTime = creatTime;
    }


    public String getAccount() {
        return Account;
    }

    public void setAccount(String account) {
        Account = account;
    }

    public String getAppVerisonCode() {
        return AppVerisonCode;
    }

    public void setAppVerisonCode(String appVerisonCode) {
        AppVerisonCode = appVerisonCode;
    }

    public String getAppVersionName() {
        return AppVersionName;
    }

    public void setAppVersionName(String appVersionName) {
        AppVersionName = appVersionName;
    }

    public String getCpuModel() {
        return CpuModel;
    }

    public void setCpuModel(String cpuModel) {
        CpuModel = cpuModel;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getIMSI() {
        return IMSI;
    }

    public void setIMSI(String IMSI) {
        this.IMSI = IMSI;
    }

    public String getPhoneBrand() {
        return PhoneBrand;
    }

    public void setPhoneBrand(String phoneBrand) {
        PhoneBrand = phoneBrand;
    }

    public String getPhoneModel() {
        return PhoneModel;
    }

    public void setPhoneModel(String phoneModel) {
        PhoneModel = phoneModel;
    }

    public String getVersionRelease() {
        return VersionRelease;
    }

    public void setVersionRelease(String versionRelease) {
        VersionRelease = versionRelease;
    }

    public String getWidthAndHeight() {
        return WidthAndHeight;
    }

    public void setWidthAndHeight(String widthAndHeight) {
        WidthAndHeight = widthAndHeight;
    }

    public String getVersionSdk() {
        return VersionSdk;
    }

    public void setVersionSdk(String versionSdk) {
        VersionSdk = versionSdk;
    }

    public String getmStrException() {
        return mStrException;
    }

    public void setmStrException(String mStrException) {
        this.mStrException = mStrException;
    }

    public String getPhoneNub() {
        return PhoneNub;
    }

    public void setPhoneNub(String phoneNub) {
        PhoneNub = phoneNub;
    }

    public String getPhoneType() {
        return PhoneType;
    }

    public void setPhoneType(String phoneType) {
        PhoneType = phoneType;
    }

    /**
     * 初始化
     * @param mCon
     */
    public void initData(Context mCon)
    {

        setAppVerisonCode("" + PhoneUtils.getVersionCode(mCon));
        setAppVersionName(PhoneUtils.getVersionName(mCon));
        setPhoneModel(Build.MODEL);
        setPhoneBrand(Build.BRAND);

        setVersionRelease(Build.VERSION.RELEASE);
        setVersionSdk(Build.VERSION.SDK_INT + "");
        setCpuModel(getCpuInfo()[0]);

        TelephonyManager mTm = (TelephonyManager)mCon.getSystemService(mCon.TELEPHONY_SERVICE);
        setIMEI(mTm.getDeviceId());
        setIMSI(mTm.getSubscriberId());
        setPhoneNub(mTm.getLine1Number());
        setPhoneType("android");
        setWidthAndHeight(PhoneUtils.getScreenWidth(mCon) + "*" + PhoneUtils.getScreenHeight(mCon));
    }
    /**
     * 手机CPU信息
     */
    private String[] getCpuInfo() {
        String str1 = "/proc/cpuinfo";
        String str2 = "";
        String[] cpuInfo = {"", ""};  //1-cpu型号  //2-cpu频率
        String[] arrayOfString;
        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            for (int i = 2; i < arrayOfString.length; i++) {
                cpuInfo[0] = cpuInfo[0] + arrayOfString[i] + " ";
            }
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            cpuInfo[1] += arrayOfString[2];
            localBufferedReader.close();
        } catch (IOException e) {
        }
        return cpuInfo;
    }
}
