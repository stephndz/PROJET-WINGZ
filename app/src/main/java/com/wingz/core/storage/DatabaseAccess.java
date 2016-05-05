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

import java.util.List;

/**
 * Created by Dasha on 01/05/16.
 */
public abstract class DatabaseAccess<T> {
    protected static final String TAG = "DatabaseAccess";
    protected SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;

    /**
     * Common column names
     */

    /**
     * Site table - Columns names
     */
    protected static class SiteEntry implements BaseColumns {
        protected static final String TABLE_NAME = "site";
        protected static final String COLUMN_ID = "id";
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
        protected static final String COLUMN_ID = "id";
        protected static final String COLUMN_CITY = "city";
        protected static final String COLUMN_PUBLIC_TRANSPORT = "public_transport";
        protected static final String COLUMN_PRIVATE_TRANSPORT = "private_transport";
        protected static final String COLUMN_HOTEL = "hotel";
        protected static final String COLUMN_RESTAURANT = "restaurant";
        protected static final String COLUMN_EVENTS = "events";
    }


    /**
     * Destination table - Columns names
     */

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    protected DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }


    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    // only Read operation from the CRUD
    public abstract long create(T obj);
    public abstract void delete(long key);
    public abstract T getOne(long key);
    public abstract List<T> getAll();
    public abstract int update(T obj);
}
