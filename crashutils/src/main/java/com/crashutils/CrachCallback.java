package com.crashutils;

import android.content.Context;
import android.util.Log;

import com.crashutils.db.CrashBaen;
import com.crashutils.db.DaoHelp;
import com.j256.ormlite.dao.Dao;
import com.zxy.recovery.callback.RecoveryCallback;
import com.zxy.recovery.core.Recovery;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by thinkpad on 2017/6/21.
 */

public  class CrachCallback implements RecoveryCallback {
    private String account;
    private Context mContext;
    private StringBuilder sb=new StringBuilder();
    public CrachCallback(Context mContext)
    {
        this.mContext=mContext;
    }
    @Override
    public void stackTrace(String stackTrace) {
        sb.append("stackTrace："+stackTrace);
    }

    @Override
    public void cause(String cause) {
        sb.append("cause："+cause);
    }

    @Override
    public void exception(String throwExceptionType, String throwClassName, String throwMethodName, int throwLineNumber) {
        sb.append("\nthrowExceptionType："+throwExceptionType);
        sb.append("\nthrowClassName："+throwClassName);
        sb.append("\nthrowMethodName："+throwMethodName);
        sb.append("\nthrowLineNumber："+throwLineNumber);
    }

    @Override
    public void throwable(Throwable throwable) {
        try {
            CrashBaen bean  = dumpPhoneInfo(throwable);
            save(bean);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private CrashBaen  dumpPhoneInfo(Throwable ex) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
        String time = df.format(new Date());// new Date()为获取当前系统时间
        CrashBaen bean = new CrashBaen();
        bean.initData(mContext);
        bean.setCreatTime(time);
        bean.setIsUpLoad(0);//设置未上传
        bean.setAccount(account==null?"":account);
        bean.setmStrException(sb.toString());
        bean.setIsdebug("1");
        return bean;
    }

    public void save(CrashBaen bean)
    {
        try {
            Dao<CrashBaen,Integer> dao= DaoHelp.getInstance(mContext).getDaoCrashBaen();
            dao.createIfNotExists(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
