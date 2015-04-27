package com.waageweb.tides.app.data;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.waageweb.tides.app.R;

import java.util.Date;

public class TidesAdapter extends CursorAdapter {

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public TidesAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        final View view = LayoutInflater.from(context).inflate(R.layout.activity_maps, parent, false);
        view.setTag(new ViewHolder(view));
        return view;
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        final String id = cursor.getString(cursor.getColumnIndex(TideStrings.Columns._ID));
        final String name = cursor.getString(cursor.getColumnIndex(TideStrings.Columns.NAME));
        final Date created = new Date(cursor.getLong(cursor.getColumnIndex(TideStrings.Columns.CREATED)));

        final String createdDateFormatted = context.getString(0, created);

        ViewHolder viewHolder = (ViewHolder) view.getTag();


    }

    private static class ViewHolder {
        public final TextView titleTextView;
        public final TextView dateTextView;

        public ViewHolder(View view) {
            titleTextView = (TextView) view.findViewById(R.id.title);
            dateTextView = (TextView) view.findViewById(R.id.date);
        }
    }
}
