package com.orochi.guiautfpr.persistence;

import android.provider.BaseColumns;

/**
 * Created by lucas on 27/05/16.
 */
public class TableData {

    public TableData(){}

    public static abstract class TableInfo implements BaseColumns {

        public static final String USERNAME = "userName";
        public static final String PASSWORD = "password";
        public static final String DATABASE = "database";
        public static final String TABLENAME = "userInfo";

    }
}
