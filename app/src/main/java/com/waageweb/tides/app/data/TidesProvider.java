package com.waageweb.tides.app.data;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class TidesProvider extends ContentProvider {
    private static final String TAG = TidesProvider.class.getSimpleName();
    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    private interface UriType {
        public static final int TIDES = 0;
    }

    public interface Uris {
        public static final String AUTHORITY = "com.waageweb.tides.app";
        public static final String TIDES = "tides";
        public static final Uri BASE = Uri.parse("content://" + Uris.AUTHORITY);
        public static final Uri TIDE_CONTENT_URI = BASE.buildUpon().appendPath(Uris.TIDES).build();
    }

    private interface ContentType {
        public static final String TIDE = String.format("%s/%s/%s", ContentResolver.CURSOR_DIR_BASE_TYPE, Uris.AUTHORITY, Uris.TIDES);
    }

    static {
        URI_MATCHER.addURI(Uris.AUTHORITY, Uris.TIDES, UriType.TIDES);
    }

    /**
     * Build uri to get a track by it id.
     * @param tideID
     * @return
     */
    public static Uri buildTideByIdUri(final String tideID) {
        return Uris.TIDE_CONTENT_URI.buildUpon().appendPath(tideID).build();
    }

    protected TidesDbHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new TidesDbHelper(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {
        return ContentType.TIDE;

    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor = dbHelper.getReadableDatabase().query(TideStrings.NAME, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri returnUri = null;
        long _id = dbHelper.getWritableDatabase().insert(TideStrings.NAME, null, values);
        if(_id > 0) {
            returnUri = uri.buildUpon().appendPath(String.valueOf(_id)).build();
        }else {
            throw new RuntimeException(String.format("Failed to insert tide [uri=%s]", uri));
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d(TAG, "Delete not implemented");
        return -1;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.d(TAG, "Update not implemented");
        return -1;
    }
}
