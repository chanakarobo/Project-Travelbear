package com.example.travelbear;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TravelbearDatabaseHelper extends SQLiteOpenHelper {


    private  static final String DB_NAME="travelbare";
    private static final int DB_VERSION=1;


    TravelbearDatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE LOCATION(_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"LOCATIONS TEXT,"
                +"DESCRIPTON INTEGER,"
                +"IMAGE_RESOURCE_ID INTEGER,"
                +"IMAGE_RESOURCE_ID_2 INTEGER,"
                +"FAVORITE NUMERIC,"
                +"BEEN NUMERIC,"
                +"LATITUDE DOUBLE,"
                +"LONGTUDE DOUBLE);");

        db.execSQL("CREATE TABLE GUIDERS(_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"NAME TEXT,"
                +"GDESCRIPTION INTEGER,"
                + "EMAIL TEXT,"
                + "MOBILE TEXT,"
                + "ADDRESS TEXT,"
                + "GIMAGE_RESOURCE_ID INTEGER);");


        db.execSQL("CREATE TABLE USERS(_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"USERNAME TEXT,"
                +"PASSWORD TEXT);");

        db.execSQL("CREATE TABLE COMMENT_TABLE(_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"LOC_ID INTEGER ,"
                +"COMMENT TEXT);");

        insertLocation(db,"Sigiriya",R.string.sigiriya,R.drawable.sigiriya,R.drawable.sigiriya2,7.94946,80.7503662);
        insertLocation(db,"Alla",R.string.loolkadura,R.drawable.alla,R.drawable.ella2,6.8666988,81.0465530);
        insertLocation(db,"Sembuwatte",R.string.sembuwatte,R.drawable.sembuwatta,R.drawable.sembuwatta2,7.4369530,80.6999564);
        insertLocation(db,"loolkadura",R.string.loolkadura,R.drawable.loolkadura,R.drawable.loolkadura2,7.1320144,80.7141218);
        insertLocation(db,"Pidurangala",R.string.pidurangala,R.drawable.pidurangala1,R.drawable.pidurangala2,7.1320144,80.7141218);
        insertLocation(db,"Riverston",R.string.riverston,R.drawable.reverston1,R.drawable.reverston2,7.1320144,80.7141218);


        insertGuiders(db,"D.D.Bandara",R.string.ddbandara,"shalisomasinghe@gmail.com","0776758123","Peradeniya,kandy",R.drawable.guid1);
        insertGuiders(db,"H.H.Ranasinghe",R.string.hhRanasinghe,"james@gmail.com","0776758123","Peradeniya,kandy",R.drawable.guid2);
        insertGuiders(db,"Decaprio",R.string.ddbandara,"james@gmail.com","0776758123","Peradeniya,kandy",R.drawable.guid3);
        insertGuiders(db,"Lara Croft",R.string.ddbandara,"james@gmail.com","0776758123","Peradeniya,kandy",R.drawable.guid4);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private static void insertLocation(SQLiteDatabase db,String location,int description,int resourceId,int resourceId2,double latitude,double longtude){

        ContentValues locationValues=new ContentValues();
        locationValues.put("LOCATIONS",location);
        locationValues.put("DESCRIPTON",description);
        locationValues.put("IMAGE_RESOURCE_ID",resourceId);
        locationValues.put("IMAGE_RESOURCE_ID_2",resourceId2);
        locationValues.put("LATITUDE",latitude);
        locationValues.put("LONGTUDE",longtude);
        db.insert("LOCATION",null,locationValues);

    }

    private static void insertGuiders(SQLiteDatabase db,String name,Integer gdescription,String email,String mobile,String address,int gresourceId){

        ContentValues guidersValues=new ContentValues();
        guidersValues.put("NAME",name);
        guidersValues.put("GDESCRIPTION",gdescription);
        guidersValues.put("EMAIL",email);
        guidersValues.put("MOBILE",mobile);
        guidersValues.put("ADDRESS",address);
        guidersValues.put("GIMAGE_RESOURCE_ID",gresourceId);
        db.insert("GUIDERS",null,guidersValues);

    }

    public boolean insertusers(String email,String password){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("USERNAME",email);
        contentValues.put("PASSWORD",password);
        long ins=db.insert("USERS",null,contentValues);
        if(ins==-1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean insertComment(int locId,String comment){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues commentValue=new ContentValues();
        commentValue.put("LOC_ID",locId);
        commentValue.put("COMMENT",comment);

        long ins=db.insert("COMMENT_TABLE",null,commentValue);

        if(ins==-1){
            return  false;
        }else{
            return true;
        }


    }
    public Boolean checkmail(String email){

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query("USERS",new String[]{"_id","USERNAME","PASSWORD"},
                "USERNAME=?",
                new String[]{email},
                null,null,null);

        if(cursor.getCount()>0){
            return false;
        }else{
            return true;
        }


    }

    public Boolean emailpassword(String email,String password){

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from USERS where USERNAME=? and PASSWORD=?",new String[]{email,password});

        if(cursor.getCount()>0){
            return true;
        }else{
            return false;
        }

    }



}
