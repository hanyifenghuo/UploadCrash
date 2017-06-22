package com.crashutils.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by thinkpad on 2017/4/24.
 */

public class DaoHelp extends OrmLiteSqliteOpenHelper {
    private static final String TABLE_NAME = "crash.db";
    private static DaoHelp instance;
    private  DaoHelp(Context context) {
        super(context, TABLE_NAME, null, 1); //必须实现父类的方法，其中第二个参数是创建的数据库名，第4个参数是版本号，用于升级等操作
    }
    public static DaoHelp getInstance(Context context){
        if(instance == null){
            synchronized (DaoHelp.class){
                if(instance == null){
                    instance = new DaoHelp(context.getApplicationContext());
                }
            }
        }
        return instance;
    }
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {                                                                            try{
//        TableUtils.createTableIfNotExists(connectionSource, CrashBaen.class);   //根据PersonalBean来进行创建操作
            TableUtils.createTableIfNotExists(connectionSource,CrashBaen.class);
        Log.i("--ormlite:", "--onCreate:");
    }catch (SQLException e){
        e.printStackTrace();
    }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try{
            TableUtils.dropTable(connectionSource, CrashBaen.class, true); //如果版本有更新则会执行onUpgrade方法，
            TableUtils.createTable(connectionSource, CrashBaen.class); //更新数据库先删除数据库再创建一个新的
            Log.i("--ormlite:", "--onUpgrade:");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    // 用单例来生成DaoHelp对象
    private static DaoHelp daohelp;
    public static synchronized DaoHelp getDaoHelp(Context context){
        if(daohelp == null){
            synchronized (DaoHelp.class){
                if(daohelp == null) daohelp = new DaoHelp((context));
            }
        }
        return daohelp;
    }
    //利用生成的daoHelp对象来生成Dao对象，该对象是处理数据库的关键要素
    private Dao<CrashBaen, Integer> dao;
    public Dao<CrashBaen,Integer> getDaoCrashBaen() throws SQLException {
        if(dao == null){
            dao = getDao(CrashBaen.class);
        }
        return dao;
    }
    //释放资源
    @Override
    public void close(){
        super.close();
        dao = null;
    }
}
