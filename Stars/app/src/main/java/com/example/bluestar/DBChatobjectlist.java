package com.example.bluestar;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//稍等，好像不需要定义数据库啊
public class DBChatobjectlist extends SQLiteOpenHelper {
    private static final int VERSION=1;
    private static final String DBNAME="Chatobjectlist.db";   //  创建数据库名叫 Chatobjectlist
    private Context mContext;

    public DBChatobjectlist(Context context){
        super(context,DBNAME,null,VERSION);
        mContext = context;
    }
    //创建数据库
    public void onCreate(SQLiteDatabase db){
        //创建密码表  pwd_tb
        db.execSQL("create table pwd_tb (pwd varchar(20) primary key)");
        //创建收入表    user_tb
        db.execSQL("create table object_tb(_id integer primary key autoincrement, money decimal," +
                " time varchar(10),type varchar(10),handler varchar(100),mark varchar(200))");
    }
    //数据库版本更新
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion)
    {
        db.execSQL("drop table if exists pwd_tb");
        db.execSQL("drop table if exists user_tb");
        onCreate(db);
    }


}

