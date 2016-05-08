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
import android.provider.BaseColumns;

import com.wingz.core.model.Destination;

import java.util.List;

/**
 * Created by Dasha on 10/04/16.
 */
public abstract class DBHelper<T> extends SQLiteOpenHelper{

    protected static final String TAG = "Wingz_db_Helper";
    protected static final int DATABASE_VERSION = 1;
    protected static final String DATABASE_NAME = "wingz.db";


    /**
     * Common column names
     */

    /**
     * Site table - Columns names
     */
    protected static class SiteEntry implements BaseColumns{
        protected static final String TABLE_NAME = "site";
        protected static final String COLUMN_TITLE = "title";
        protected static final String COLUMN_TYPE = "type";
        protected static final String COLUMN_CONTENT = "content";
        protected static final String COLUMN_LATITUDE = "latitude";
        protected static final String COLUMN_LONGITUDE = "longitude";
        protected static final String COLUMN_RADIUS = "radius";
        protected static final String COLUMN_EVENTS = "events";
    }

    protected static class DestinationEntry implements BaseColumns{
        protected static final String TABLE_NAME = "destination";
        protected static final String COLUMN_CITY = "city";
        protected static final String COLUMN_PUBLIC_TRANSPORT = "protected transport";
        protected static final String COLUMN_PRIVATE_TRANSPORT = "taxi";
        protected static final String COLUMN_HOTEL = "hotel";
        protected static final String COLUMN_RESTAURANT = "restaurant";
        protected static final String COLUMN_EVENTS = "events";
    }


    /**
     * Destination table - Columns names
     */


    /**
     * Ligne table - Columns names
     */
    // TODO: Recreate ligne table
    /**
     * Table create statements
     */
    private static final String CREATE_TABLE_SITE = "CREATE TABLE "+ SiteEntry.TABLE_NAME
            + "(" + SiteEntry._ID + " INTEGER PRIMARY KEY,"
            + SiteEntry.COLUMN_TITLE + "TEXT,"
            + SiteEntry.COLUMN_TYPE + " TEXT,"
            + SiteEntry.COLUMN_CONTENT + "TEXT,"
            + SiteEntry.COLUMN_LATITUDE + " REAL,"
            + SiteEntry.COLUMN_LONGITUDE + "REAL,"
            + SiteEntry.COLUMN_RADIUS + " REAL,"
            + SiteEntry.COLUMN_EVENTS + "TEXT,"
            //+ KEY_CREATED_AT + " DATETIME"
            + ")";
    private static final String CREATE_TABLE_DESTINATION = "CREATE TABLE "+ DestinationEntry.TABLE_NAME
            + "(" + DestinationEntry._ID + " INTEGER PRIMARY KEY,"
            + DestinationEntry.COLUMN_CITY + " TEXT,"
            + DestinationEntry.COLUMN_PUBLIC_TRANSPORT + "TEXT,"
            + DestinationEntry.COLUMN_PRIVATE_TRANSPORT + " TEXT,"
            + DestinationEntry.COLUMN_HOTEL + "TEXT,"
            + DestinationEntry.COLUMN_RESTAURANT + " TEXT,"
            + DestinationEntry.COLUMN_EVENTS + "TEXT,"
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
        db.execSQL("DROP TABLE IF EXISTS "+ SiteEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ DestinationEntry.TABLE_NAME);

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