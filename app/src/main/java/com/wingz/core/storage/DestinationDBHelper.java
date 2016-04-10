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

import com.wingz.core.model.Destination;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dasha on 10/04/16.
 */
public class DestinationDBHelper extends DBHelper<Destination> {
    /**
     * @param context
     */
    public DestinationDBHelper(Context context) {
        super(context);
    }

    @Override
    public long create(Destination destination) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DestinationEntry.COLUMN_CITY, destination.getCity());
        values.put(DestinationEntry.COLUMN_PUBLIC_TRANSPORT,destination.getPublic_transport());
        values.put(DestinationEntry.COLUMN_PRIVATE_TRANSPORT,destination.getTaxi());
        values.put(DestinationEntry.COLUMN_HOTEL,destination.getHotel());
        values.put(DestinationEntry.COLUMN_RESTAURANT,destination.getRestaurant());
        values.put(DestinationEntry.COLUMN_EVENTS,destination.getEvents());

        return db.insert(DestinationEntry.TABLE_NAME, null, values);
    }

    @Override
    public void delete(long key) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DestinationEntry.TABLE_NAME, DestinationEntry._ID + " = ?",
                new String[] { String.valueOf(key) });
    }

    @Override
    public Destination getOne(long key) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + DestinationEntry.TABLE_NAME + " WHERE "
                + DestinationEntry._ID + " = " + key;

        Log.d(TAG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Destination destination = null;
        if (c != null) {
            destination = new Destination(c.getLong(c.getColumnIndex(DestinationEntry._ID)),
                    c.getString(c.getColumnIndex(DestinationEntry.COLUMN_CITY)),
                    c.getString(c.getColumnIndex(DestinationEntry.COLUMN_PRIVATE_TRANSPORT)),
                    c.getString(c.getColumnIndex(DestinationEntry.COLUMN_PRIVATE_TRANSPORT)),
                    c.getString(c.getColumnIndex(DestinationEntry.COLUMN_HOTEL)),
                    c.getString(c.getColumnIndex(DestinationEntry.COLUMN_RESTAURANT)),
                    c.getString(c.getColumnIndex(DestinationEntry.COLUMN_EVENTS))
            );
        }

        return destination;

    }

    @Override
    public List<Destination> getAll() {
        List<Destination> destinations = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + DestinationEntry.TABLE_NAME;

        Log.d(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Destination dest = new Destination(c.getLong(c.getColumnIndex(DestinationEntry._ID)),
                        c.getString(c.getColumnIndex(DestinationEntry.COLUMN_CITY)),
                        c.getString(c.getColumnIndex(DestinationEntry.COLUMN_PRIVATE_TRANSPORT)),
                        c.getString(c.getColumnIndex(DestinationEntry.COLUMN_PRIVATE_TRANSPORT)),
                        c.getString(c.getColumnIndex(DestinationEntry.COLUMN_HOTEL)),
                        c.getString(c.getColumnIndex(DestinationEntry.COLUMN_RESTAURANT)),
                        c.getString(c.getColumnIndex(DestinationEntry.COLUMN_EVENTS)));

                // adding to destination list
                destinations.add(dest);
            } while (c.moveToNext());
        }

        return destinations;

    }

    @Override
    public int update(Destination destination) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DestinationEntry._ID, destination.getId());
        values.put(DestinationEntry.COLUMN_CITY, destination.getCity());
        values.put(DestinationEntry.COLUMN_PUBLIC_TRANSPORT, destination.getPublic_transport());
        values.put(DestinationEntry.COLUMN_PRIVATE_TRANSPORT, destination.getTaxi());
        values.put(DestinationEntry.COLUMN_HOTEL, destination.getHotel());
        values.put(DestinationEntry.COLUMN_RESTAURANT,destination.getRestaurant());
        values.put(DestinationEntry.COLUMN_EVENTS,destination.getEvents());

        // updating row
        return db.update(DestinationEntry.TABLE_NAME, values, DestinationEntry._ID + " = ?",
                new String[] { String.valueOf(destination.getId()) });
    }
}
