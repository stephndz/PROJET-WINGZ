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
public class SiteAccess extends DatabaseAccess<Site> {

    private static SiteAccess instance;
    /**
     * @param context
     */
    public SiteAccess(Context context) {
        super(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static SiteAccess getInstance(Context context) {
        if (instance == null) {
            instance = new SiteAccess(context);
        }
        return instance;
    }

    @Override
    public long create(Site site) {
        SQLiteDatabase db = this.openHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SiteEntry.COLUMN_TITLE, site.getTitle());
        values.put(SiteEntry.COLUMN_TYPE, site.getType());
        values.put(SiteEntry.COLUMN_CONTENT, site.getContent());
        values.put(SiteEntry.COLUMN_LATITUDE, site.getLatitude());
        values.put(SiteEntry.COLUMN_LONGITUDE, site.getLongitude());
        values.put(SiteEntry.COLUMN_RADIUS, site.getRadius());
        values.put(SiteEntry.COLUMN_EVENTS, site.getEvents());

        return db.insert(SiteEntry.TABLE_NAME, null, values);
    }

    @Override
    public void delete(long key) {

        SQLiteDatabase db = this.openHelper.getWritableDatabase();
        db.delete(SiteEntry.TABLE_NAME, SiteEntry.COLUMN_ID + " = ?",
                new String[] { String.valueOf(key) });
    }

    @Override
    public Site getOne(long key) {
        SQLiteDatabase db = this.openHelper.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + SiteEntry.TABLE_NAME + " WHERE "
                + SiteEntry.COLUMN_ID + " = " + key;

        Log.d(TAG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Site site = null;
        if (c != null) {
            site = new Site(c.getLong(c.getColumnIndex(SiteEntry.COLUMN_ID)),
                    c.getString(c.getColumnIndex(SiteEntry.COLUMN_TITLE)),
                    c.getString(c.getColumnIndex(SiteEntry.COLUMN_TYPE)),
                    c.getString(c.getColumnIndex(SiteEntry.COLUMN_CONTENT)),
                    c.getDouble(c.getColumnIndex(SiteEntry.COLUMN_LATITUDE)),
                    c.getDouble(c.getColumnIndex(SiteEntry.COLUMN_LONGITUDE)),
                    c.getDouble(c.getColumnIndex(SiteEntry.COLUMN_RADIUS)),
                    c.getString(c.getColumnIndex(SiteEntry.COLUMN_EVENTS))
            );
        }

        return site;
    }

    @Override
    public List<Site> getAll() {
        List<Site> sites = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + SiteEntry.TABLE_NAME;

        Log.d(TAG, selectQuery);

        SQLiteDatabase db = this.openHelper.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);


        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Site site = new Site(c.getLong(c.getColumnIndex(SiteEntry.COLUMN_ID)),
                        c.getString(c.getColumnIndex(SiteEntry.COLUMN_TITLE)),
                        c.getString(c.getColumnIndex(SiteEntry.COLUMN_TYPE)),
                        c.getString(c.getColumnIndex(SiteEntry.COLUMN_CONTENT)),
                        c.getDouble(c.getColumnIndex(SiteEntry.COLUMN_LATITUDE)),
                        c.getDouble(c.getColumnIndex(SiteEntry.COLUMN_LONGITUDE)),
                        c.getDouble(c.getColumnIndex(SiteEntry.COLUMN_RADIUS)),
                        c.getString(c.getColumnIndex(SiteEntry.COLUMN_EVENTS))
                );

                // adding to destination list
                sites.add(site);
            } while (c.moveToNext());
        }

        return sites;
    }

    @Override
    public int update(Site site) {
        SQLiteDatabase db = this.openHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SiteEntry.COLUMN_ID, site.getId());
        values.put(SiteEntry.COLUMN_TITLE, site.getTitle());
        values.put(SiteEntry.COLUMN_TYPE, site.getType());
        values.put(SiteEntry.COLUMN_CONTENT, site.getContent());
        values.put(SiteEntry.COLUMN_LATITUDE, site.getLatitude());
        values.put(SiteEntry.COLUMN_RADIUS, site.getRadius());
        values.put(SiteEntry.COLUMN_EVENTS, site.getEvents());



        // updating row
        return db.update(SiteEntry.TABLE_NAME, values, SiteEntry.COLUMN_ID + " = ?",
                new String[] { String.valueOf(site.getId()) });
    }

}
