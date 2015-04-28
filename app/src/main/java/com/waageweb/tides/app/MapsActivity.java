package com.waageweb.tides.app;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.waageweb.tides.app.data.TideStrings;
import com.waageweb.tides.app.data.TidesProvider;
import com.waageweb.tides.app.data.TidesTask;

public class MapsActivity extends FragmentActivity {

    public static String NAME = "name";
    public static String HIGH_LOW = "high_low";

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private  LatLng latLng = new LatLng(60.389444, 5.33);

    private void updateTides() {
        TidesTask weatherTask = new TidesTask(this, new TidesTask.CallBack() {
            @Override
            public void onTidesUpdated() {
                if( mMap != null){
                    setUpMap();
                }else {
                    setUpMapIfNeeded();
                }

            }
        });
        weatherTask.execute("60.389444", "5.33");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        //setUpMapIfNeeded();
        updateTides();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }else  {
            setUpMap();
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {

        mMap.clear();

        GoogleMap.OnInfoWindowClickListener clickListener = new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                marker.getId();
                Intent intent = new Intent(MapsActivity.this, DetailActivity.class);
                intent.putExtra(NAME,marker.getTitle());
                intent.putExtra(HIGH_LOW, marker.getSnippet());
                startActivity(intent);
            }
        };

       mMap.setOnInfoWindowClickListener(clickListener);

        String name = "Venter på lokasjonsdata...";
        CameraUpdate center= CameraUpdateFactory.newLatLngZoom(latLng, 15);

        mMap.animateCamera(center);

        String body = "";
        String id = "";

        Cursor cursor = getContentResolver().query(TidesProvider.Uris.TIDE_CONTENT_URI, null, null, null, null);
        int i = 0;
        // TODO: for now: read the 4 last tides. Later: fix this so we dont get a flooded database later on.
        while (cursor.moveToNext() && i < 4){
            name = cursor.getString(cursor.getColumnIndex(TideStrings.Columns.NAME));
            body = body + cursor.getString(cursor.getColumnIndex(TideStrings.Columns.VALUE)) + "cm " + "\n";
            id = cursor.getString(cursor.getColumnIndex(TideStrings.Columns._ID));
            i++;
        }

        MarkerOptions marker = new MarkerOptions().position(latLng).title(name);
        marker.snippet(body);
        mMap.addMarker(marker).showInfoWindow();
    }
}
