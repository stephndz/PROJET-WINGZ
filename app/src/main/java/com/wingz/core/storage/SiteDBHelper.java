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

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.wingz.core.model.Site;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dasha on 10/04/16.
 */
public class SiteDBHelper extends DBHelper<Site>{
    /**
     * @param context
     */
    public SiteDBHelper(Context context) {
        super(context);
    }

    @Override
    public long create(Site site) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SITE_COLUMN_PATH, site.getPath());
        values.put(SITE_COLUMN_TITLE, site.getTitle());
        values.put(SITE_COLUMN_TYPE, site.getType());
        values.put(SITE_COLUMN_CONTENT, site.getContent());
        values.put(SITE_COLUMN_LATITUDE, site.getLatitude());
        values.put(SITE_COLUMN_LONGITUDE, site.getLongitude());
        values.put(SITE_COLUMN_RADIUS, site.getRadius());
        values.put(SITE_COLUMN_EVENTS, site.getEvents());

        return db.insert(SITE_TABLE_NAME, null, values);
    }

    @Override
    public void delete(long key) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SITE_TABLE_NAME, KEY_ID + " = ?",
                new String[] { String.valueOf(key) });
    }

    @Override
    public Site getOne(long key) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + SITE_TABLE_NAME + " WHERE "
                + KEY_ID + " = " + key;

        Log.d(TAG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Site site = null;
        if (c != null) {
            site = new Site(c.getInt(c.getColumnIndex(KEY_ID)),
                    c.getString(c.getColumnIndex(SITE_COLUMN_PATH)),
                    c.getString(c.getColumnIndex(SITE_COLUMN_TITLE)),
                    c.getString(c.getColumnIndex(SITE_COLUMN_TYPE)),
                    c.getString(c.getColumnIndex(SITE_COLUMN_CONTENT)),
                    c.getDouble(c.getColumnIndex(SITE_COLUMN_LATITUDE)),
                    c.getDouble(c.getColumnIndex(SITE_COLUMN_LONGITUDE)),
                    c.getDouble(c.getColumnIndex(SITE_COLUMN_RADIUS)),
                    c.getString(c.getColumnIndex(SITE_COLUMN_EVENTS))
            );
        }

        return site;
    }

    @Override
    public List<Site> getAll() {
        List<Site> sites = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + SITE_TABLE_NAME;

        Log.d(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Site site = new Site(c.getInt(c.getColumnIndex(KEY_ID)),
                        c.getString(c.getColumnIndex(SITE_COLUMN_PATH)),
                        c.getString(c.getColumnIndex(SITE_COLUMN_TITLE)),
                        c.getString(c.getColumnIndex(SITE_COLUMN_TYPE)),
                        c.getString(c.getColumnIndex(SITE_COLUMN_CONTENT)),
                        c.getDouble(c.getColumnIndex(SITE_COLUMN_LATITUDE)),
                        c.getDouble(c.getColumnIndex(SITE_COLUMN_LONGITUDE)),
                        c.getDouble(c.getColumnIndex(SITE_COLUMN_RADIUS)),
                        c.getString(c.getColumnIndex(SITE_COLUMN_EVENTS))
                );

                // adding to destination list
                sites.add(site);
            } while (c.moveToNext());
        }

        return sites;
    }

    @Override
    public int update(Site site) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, site.getId());
        values.put(SITE_COLUMN_PATH, site.getPath());
        values.put(SITE_COLUMN_TITLE, site.getTitle());
        values.put(SITE_COLUMN_TYPE, site.getType());
        values.put(SITE_COLUMN_CONTENT, site.getContent());
        values.put(SITE_COLUMN_LATITUDE, site.getLatitude());
        values.put(SITE_COLUMN_RADIUS, site.getRadius());
        values.put(SITE_COLUMN_EVENTS, site.getEvents());



        // updating row
        return db.update(SITE_TABLE_NAME, values, KEY_ID + " = ?",
                new String[] { String.valueOf(site.getId()) });
    }

}
