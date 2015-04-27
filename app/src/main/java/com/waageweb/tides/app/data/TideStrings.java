package com.waageweb.tides.app.data;

import android.provider.BaseColumns;

public class TideStrings {

        public static final String NAME = "tides";

        public interface Columns extends BaseColumns {
            public static final String _ID = "_id";
            public static final String NAME = "name";
            public static final String CREATED = "created";
            public static final String HIGHLOW = "high_low";
            public static final String VALUE = "value";
            public static final String UNIT = "unit";


        }
}
