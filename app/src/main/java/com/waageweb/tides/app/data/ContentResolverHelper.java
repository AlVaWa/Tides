package com.waageweb.tides.app.data;

import android.content.ContentResolver;
import android.database.Cursor;


public class ContentResolverHelper {
    final ContentResolver contentResolver;

    public ContentResolverHelper(final ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    public Tides getTrackById(final int tidesID) {
        Cursor cursor = contentResolver.query(TidesProvider.buildTideByIdUri(String.valueOf(tidesID)),
                new String[] {TideStrings.Columns._ID, TideStrings.Columns.NAME },
                "_id=?",
                new String[]{ String.valueOf(tidesID) },
                null);

        if(cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex(TideStrings.Columns.NAME));
            long created = cursor.getLong(cursor.getColumnIndex(TideStrings.Columns.CREATED));

            final Tides tide = new Tides();
            tide.setId(tidesID);
            tide.setName(name);
            tide.setCreated(created);

            return tide;
        }
        else {
            return null;
        }
    }
}
