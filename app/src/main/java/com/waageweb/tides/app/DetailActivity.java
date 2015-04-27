/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.waageweb.tides.app;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.nio.charset.MalformedInputException;


public class DetailActivity extends ActionBarActivity {

    String title = "";
    String highLow = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.

            Intent intent = getIntent();
            String osdfjkhgfo = intent.getStringExtra(MapsActivity.NAME);

            Bundle arguments = new Bundle();
            arguments.putString(MapsActivity.NAME, intent.getStringExtra(MapsActivity.NAME));
            arguments.putString(MapsActivity.HIGH_LOW, intent.getStringExtra(MapsActivity.HIGH_LOW));

            DetailFrament fragment = new DetailFrament();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.tide_detail_container, fragment)
                    .commit();
        }
    }


    public static class DetailFrament extends Fragment {

        private Uri mUri;

        public static String DETAIL_URI = "URI";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.detail_fragment, container, false);
            TextView title = (TextView) rootView.findViewById(R.id.title);
            TextView snippet = (TextView) rootView.findViewById(R.id.high_low);

            Bundle arguments = getArguments();
            if (arguments != null) {
                mUri = arguments.getParcelable(DetailFrament.DETAIL_URI);
                title.setText(arguments.getString(MapsActivity.NAME));
                snippet.setText(arguments.getString(MapsActivity.HIGH_LOW));
            }

            return rootView;
        }
    }

}