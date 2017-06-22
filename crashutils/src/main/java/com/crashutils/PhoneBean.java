package com.crashutils;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by thinkpad on 2016/9/5.
 */
public class PhoneBean {

    private String IMEI;
    /**
     * startsWith
     * 移动46000、46002
     * 联通46001
     * 电信46003
     */
    private String IMSI;
    private String AppVerisonCode;
    private String AppVersionName;
    /**
     * 手机型号
     */
    private String PhoneModel;
    /**
     * 获取版本号
     */
    private String VersionRelease;

    /**
     *
     * 品牌
     */
    private String PhoneBrand;

    /**
     * 手机号码
     */
    private String PhoneNub;

    /**
     * cpu型号
     */
    private String CpuModel;

    /**
     * sdk版本信息
     */
    private String VersionSdk;

    /**
     * 报错信息
     */
    private String mStrException;

    /**
     * 手机宽搞
     */
    private String WidthAndHeight;

    /**
     * 用户账号
     */
    private String Account;

    /**
     * 手机型号
     */
    private String PhoneType;

    public String getmStrException() {
        return mStrException;
    }

    public void setmStrException(String mStrException) {
        this.mStrException = mStrException;
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

    public String getPhoneModel() {
        return PhoneModel;
    }

    public void setPhoneModel(String phoneModel) {
        PhoneModel = phoneModel;
    }



    public String getPhoneBrand() {
        return PhoneBrand;
    }

    public void setPhoneBrand(String phoneBrand) {
        PhoneBrand = phoneBrand;
    }

    public String getPhoneNub() {
        return PhoneNub;
    }

    public void setPhoneNub(String phoneNub) {
        PhoneNub = phoneNub;
    }

    public String getCpuModel() {
        return CpuModel;
    }

    public void setCpuModel(String cpuModel) {
        CpuModel = cpuModel;
    }

    public String getVersionRelease() {
        return VersionRelease;
    }

    public void setVersionRelease(String versionRelease) {
        VersionRelease = versionRelease;
    }

    public String getVersionSdk() {
        return VersionSdk;
    }

    public void setVersionSdk(String versionSdk) {
        VersionSdk = versionSdk;
    }

    public String getWidthAndHeight() {
        return WidthAndHeight;
    }

    public void setWidthAndHeight(String widthAndHeight) {
        WidthAndHeight = widthAndHeight;
    }

    public String getAccount() {
        return Account;
    }

    public void setAccount(String account) {
        Account = account;
    }

    public String getPhoneType() {
        return PhoneType;
    }

    public void setPhoneType(String phoneType) {
        PhoneType = phoneType;
    }

    @Override
    public String toString() {
        return "PhoneBean{" +
                "IMEI='" + IMEI + '\'' +
                ", IMSI='" + IMSI + '\'' +
                ", AppVerisonCode='" + AppVerisonCode + '\'' +
                ", AppVersionName='" + AppVersionName + '\'' +
                ", PhoneModel='" + PhoneModel + '\'' +
                ", VersionRelease='" + VersionRelease + '\'' +
                ", PhoneBrand='" + PhoneBrand + '\'' +
                ", PhoneNub='" + PhoneNub + '\'' +
                ", CpuModel='" + CpuModel + '\'' +
                ", VersionSdk='" + VersionSdk + '\'' +
                ", mStrException='" + mStrException + '\'' +
                ", WidthAndHeight='" + WidthAndHeight + '\'' +
                ", Account='" + Account + '\'' +
                ", PhoneType='" + PhoneType + '\'' +
                '}';
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

//        Loginer mLoginer=MyApplication.getInstance().getMloginer();
//        if (mLoginer!=null)
//        {
//            setAccount(mLoginer.getDealer_name());
//        }
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
