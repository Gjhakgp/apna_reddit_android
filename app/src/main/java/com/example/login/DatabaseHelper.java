package com.example.login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper  extends SQLiteOpenHelper {
    public static  String USERNAME;
    public static int post_count;
    public static int comment_count;
    public static final String DATABASE_NAME="database.db";
    public static final String TABLE_NAME="user";
    public static final String COL_1="name";
    public static final String COL_2="username";
    public static final String COL_3="password";
    public static final String COL_4="phone";
    public static final String COL_5="email";
    public static final String COL_6="city";
    public static final String COL_7="state";

    public static final String TABLE_NAME_COMMENT="comment";
    public static final String COL_8="c_id";
    public static final String COL_9="Text";
//    public static final String COL_10="parent_id";
    public static final String COL_11="post_id";

    public static final String TABLE_NAME_POST="post";
    public static final String COL_12="subreddit_name";

    public static final String TABLE_NAME_SUBREDDIT="subreddit";
//    public static final String TABLE_NAME_SUBSCRIBER="subscriber";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE user (name VARCHAR,username VARCHAR PRIMARY KEY,password VARCHAR" +
                ", phone VARCHAR, email VARCHAR,city VARCHAR,state VARCHAR)");
        db.execSQL("CREATE TABLE comment (c_id VARCHAR PRIMARY KEY,username VARCHAR ,Text Varchar," +
                "post_id VARCHAR,FOREIGN KEY (username)REFERENCES user(username),FOREIGN KEY (post_id) REFERENCES post(post_id))");
        db.execSQL("CREATE TABLE post (post_id VARCHAR PRIMARY KEY,Text VARCHAR," +
                "username VARCHAR ,subreddit_name VARCHAR ,FOREIGN KEY (username) REFERENCES user(username),FOREIGN KEY (subreddit_name)references subreddit(subreddit_name))");
        db.execSQL("CREATE TABLE subreddit (subreddit_name VARCHAR PRIMARY KEY,username VARCHAR, FOREIGN KEY(username) references user(username))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+ TABLE_NAME);
        onCreate(db);
    }

    public long addUser(String name,String username, String password,String phone,String email,String city,String state){
        SQLiteDatabase db1=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("name",name);
        contentValues.put("username",username);
        contentValues.put("password",password);
        contentValues.put("phone",phone);
        contentValues.put("email",email);
        contentValues.put("city",city);
        contentValues.put("state",state);
        long res=db1.insert("user",null,contentValues);
        db1.close();
        return res;
    }

    public boolean checkUser(String username,String password){
        USERNAME=username;
        String[] columns={COL_1};
        SQLiteDatabase db=this.getReadableDatabase();
        String selection=COL_2+"=?"+" and " + COL_3+"=?";
        String[] selectionArgs={username,password};
        Cursor cursor=db.query(TABLE_NAME,columns,selection,selectionArgs,null,null,null);
        int count=cursor.getCount();
        cursor.close();
        db.close();
        if(count>0){
            return true;
        }
        else{
            return false;
        }
    }
    public String getUsername(){
        return USERNAME;
    }

    public long addPost(String post,String username,String subreddit_name){
//        String post_id=String.valueOf(post_count);
        String post_id=post+username+subreddit_name;
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("post_id",post_id);
        contentValues.put("Text",post);
        contentValues.put("username",username);
        contentValues.put("subreddit_name",subreddit_name);
        long res=db.insert("post",null,contentValues);
        db.close();
        if(res>=0){
            post_count=post_count+1;
        }
        return res;
    }
    public long addComment(String username,String comment,String post_id){
//        String cmnt_id=String.valueOf(comment_count);
        String cmnt_id=username+comment+post_id;
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("c_id",cmnt_id);
        contentValues.put("username",username);
        contentValues.put("Text",comment);
        contentValues.put("post_id",post_id);
        long res=db.insert("comment",null,contentValues);
        db.close();
        if(res>=0){
            comment_count=comment_count+1;
        }
        return  res;
    }

    public long addSubreddit(String subreddit_name,String username){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("subreddit_name",subreddit_name);
        contentValues.put("username",username);
        long res=db.insert("subreddit",null,contentValues);
        db.close();
        return res;
    }

    public String[][] getAllPost(String subreddit_name){
        SQLiteDatabase db=this.getReadableDatabase();
        String[] columns={COL_9,COL_11};
        String selection=COL_12+"=?";
        String[] selectionArgs={subreddit_name};
        Cursor cursor=db.query(TABLE_NAME_POST,columns,selection,selectionArgs,null,null,null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        if(cursor.getCount()==0){
            return null;
        }
        String[][] data= new String[cursor.getCount()][2];
        int i=0;
        String post_id=new String();
        String post=new String();
        do{
            post_id=cursor.getString(1);
            data[i][0]=post_id;
            post=cursor.getString(0);
            data[i][1]=post;
            i=i+1;
        }while (cursor.moveToNext());
        return data;
    }
//
    public String[] getComment(String post_id){
        SQLiteDatabase db=this.getReadableDatabase();
        String[] columns={COL_9};
        String selection=COL_11+"=?";
        String[] selectionArgs={post_id};
        Cursor cursor=db.query(TABLE_NAME_COMMENT,columns,selection,selectionArgs,null,null,null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        if(cursor.getCount()==0){
            return null;
        }
        String[] data=new String[cursor.getCount()];
        int i=0;
        do{
            data[i]=cursor.getString(0);
            i=i+1;
        }while(cursor.moveToNext());
        return data;
    }

    public String[] getSubreddit(){
        SQLiteDatabase db=this.getReadableDatabase();
        String[] columns={COL_12};
        Cursor cursor=db.query(TABLE_NAME_SUBREDDIT,columns,null,null,null,null,null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        if(cursor.getCount()==0){
            return null;
        }
        String[] data=new String[cursor.getCount()];
        int i=0;
        do{
            data[i]=cursor.getString(0);
            i=i+1;
        }while(cursor.moveToNext());
        return data;
    }
//    public StringBuilder getPost(String username){
//        SQLiteDatabase db=this.getReadableDatabase();
//        String[] columns={COL_9,COL_11};
//        String selection=COL_2+"=?";
//        String[] selectionArgs={username};
//        Cursor cursor=db.query(TABLE_NAME_POST,columns,selection,selectionArgs,null,null,null);
//        if(cursor!=null){
//            cursor.moveToFirst();
//        }
//        StringBuilder builder =new StringBuilder();
//        do{
//            String post=cursor.getString(1);
//            builder.append(post).append("\n");
//        }while(cursor.moveToNext());
//        return builder;
//    }
//    public StringBuilder getPostId(String username){
//        SQLiteDatabase db=this.getReadableDatabase();
//        String[] columns={COL_9,COL_11};
//        String selection=COL_2+"=?";
//        String[] selectionArgs={username};
//        Cursor cursor=db.query(TABLE_NAME_POST,columns,selection,selectionArgs,null,null,null);
//        if(cursor!=null){
//            cursor.moveToFirst();
//        }
//        StringBuilder builder =new StringBuilder();
//        do{
//            String post=cursor.getString(0);
//            builder.append(post).append("\n");
//        }while(cursor.moveToNext());
//        return builder;
//    }


//    public StringBuilder getCommentbyUsername(String username){
//        SQLiteDatabase db=this.getReadableDatabase();
//        String[] columns={COL_9};
//        String selection=COL_2+"=?";
//        String[] selectionArgs={username};
//        Cursor cursor=db.query(TABLE_NAME_COMMENT,columns,selection,selectionArgs,null,null,null);
//        if(cursor!=null){
//            cursor.moveToFirst();
//        }
//        StringBuilder builder =new StringBuilder();
//        do{
//            String cmnt=cursor.getString(2);
//            builder.append(cmnt).append("\n");
//        }while(cursor.moveToNext());
//        return builder;
//    }


}
