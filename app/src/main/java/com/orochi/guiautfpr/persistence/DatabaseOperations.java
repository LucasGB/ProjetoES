package com.orochi.guiautfpr.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by lucas on 27/05/16.
 */
public class DatabaseOperations extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public String CREATE_QUERY = "CREATE TABLE " + TableData.TableInfo.TABLENAME +
                                    "(" + TableData.TableInfo.USERNAME + " TEXT," +
                                        TableData.TableInfo.PASSWORD + " TEXT);";

    public DatabaseOperations(Context context) {
        super(context, TableData.TableInfo.DATABASE, null, DATABASE_VERSION);
        Log.d("Database operations", "Table created");
    }

    @Override
    public void onCreate(SQLiteDatabase sdb) {

        sdb.execSQL(CREATE_QUERY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void deleteLoginInformation(DatabaseOperations dbo){

        SQLiteDatabase sq = dbo.getWritableDatabase();
        sq.delete(TableData.TableInfo.TABLENAME, null, null);
    }

    public void rewriteInformation(DatabaseOperations dbo, String name, String pass){
        SQLiteDatabase sq = dbo.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TableData.TableInfo.USERNAME, name);
        cv.put(TableData.TableInfo.PASSWORD, pass);
        sq.update(TableData.TableInfo.TABLENAME, cv, null, null);
    }

    public void putInformation(DatabaseOperations dbo, String name, String pass){
        SQLiteDatabase sq = dbo.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TableData.TableInfo.USERNAME, name);
        cv.put(TableData.TableInfo.PASSWORD, pass);
        sq.insert(TableData.TableInfo.TABLENAME, null, cv);
        Log.d("Database operations", "Row inserted");
    }

    public Cursor getInformation(DatabaseOperations dbo){
        SQLiteDatabase sq = dbo.getReadableDatabase();
        String[] columns = {TableData.TableInfo.USERNAME, TableData.TableInfo.PASSWORD };
        Cursor cr = sq.query(TableData.TableInfo.TABLENAME, columns, null, null, null, null, null);
        return cr;
    }
}
