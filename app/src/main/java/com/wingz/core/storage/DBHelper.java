/*
 * Copyright (c) 2016. The Wingz Project
 * Developed by Wingz Team.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wingz.core.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

/**
 * Created by Dasha on 10/04/16.
 */
public abstract class DBHelper<T> extends SQLiteOpenHelper{

    protected static final String TAG = "Wingz_db_Helper";
    protected static final int DATABASE_VERSION = 2;
    protected static final String DATABASE_NAME = "wingz_database";

    /**
     * Tables names
     */
    protected static final String SITE_TABLE_NAME = "Site";
    protected static final String DESTINATION_TABLE_NAME = "Destination";
    //public static final String LIGNE_TABLE_NAME = "ligne";

    /**
     * Common column names
     */
    // Common column names
    protected static final String KEY_ID = "id";
    protected static final String KEY_CREATED_AT = "created_at";
    /**
     * Site table - Columns names
     */
    protected static final String SITE_COLUMN_PATH = "path";
    protected static final String SITE_COLUMN_TITLE = "title";
    protected static final String SITE_COLUMN_TYPE = "type";
    protected static final String SITE_COLUMN_CONTENT = "content";
    protected static final String SITE_COLUMN_LATITUDE = "latitude";
    protected static final String SITE_COLUMN_LONGITUDE = "longitude";
    protected static final String SITE_COLUMN_RADIUS = "radius";
    protected static final String SITE_COLUMN_EVENTS = "events";


    /**
     * Destination table - Columns names
     */

    protected static final String DESTINATION_COLUMN_CITY = "city";
    protected static final String DESTINATION_COLUMN_PUBLIC_TRANSPORT = "protected transport";
    protected static final String DESTINATION_COLUMN_PRIVATE_TRANSPORT = "taxi";
    protected static final String DESTINATION_COLUMN_HOTEL = "hotel";
    protected static final String DESTINATION_COLUMN_RESTAURANT = "restaurant";
    protected static final String DESTINATION_COLUMN_EVENTS = "events";

    /**
     * Ligne table - Columns names
     */
    // TODO: Recreate ligne table
    /**
     * Table create statements
     */
    private static final String CREATE_TABLE_SITE = "CREATE TABLE "+ SITE_TABLE_NAME
            + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + SITE_COLUMN_PATH + " TEXT,"
            + SITE_COLUMN_TITLE + "TEXT,"
            + SITE_COLUMN_TYPE + " TEXT,"
            + SITE_COLUMN_CONTENT + "TEXT,"
            + SITE_COLUMN_LATITUDE + " REAL,"
            + SITE_COLUMN_LONGITUDE + "REAL,"
            + SITE_COLUMN_RADIUS + " REAL,"
            + SITE_COLUMN_EVENTS + "TEXT,"
            //+ KEY_CREATED_AT + " DATETIME"
            + ")";
    private static final String CREATE_TABLE_DESTINATION = "CREATE TABLE "+ DESTINATION_TABLE_NAME
            + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + DESTINATION_COLUMN_CITY + " TEXT,"
            + DESTINATION_COLUMN_PUBLIC_TRANSPORT + "TEXT,"
            + DESTINATION_COLUMN_PRIVATE_TRANSPORT + " TEXT,"
            + DESTINATION_COLUMN_HOTEL + "TEXT,"
            + DESTINATION_COLUMN_RESTAURANT + " TEXT,"
            + SITE_COLUMN_EVENTS + "TEXT,"
            //+ KEY_CREATED_AT + " DATETIME"
            + ")";
    /**
     *
     * @param context
     */

    public DBHelper(Context context){
        super(context,DATABASE_NAME,null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SITE);
        db.execSQL(CREATE_TABLE_DESTINATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ SITE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ DESTINATION_TABLE_NAME);

        // create new tables
        onCreate(db);
    }

    // Closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    // only Read operation from the CRUD
    public abstract long create(T obj);
    public abstract void delete(long key);
    public abstract T getOne(long key);
    public abstract List<T> getAll();
    public abstract int update(T obj);


}
