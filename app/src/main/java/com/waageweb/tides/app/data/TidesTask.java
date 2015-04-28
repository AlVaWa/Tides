package com.waageweb.tides.app.data;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.thoughtworks.xstream.XStream;
import com.waageweb.tides.app.data.dataModel.LocationData;
import com.waageweb.tides.app.data.dataModel.Tide;
import com.waageweb.tides.app.data.dataModel.WaterLevel;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

/**
 * Created by AleksanderVatleWaage on 27.04.15.
 */
public class TidesTask  extends AsyncTask<String, Void, Void> {

    private final CallBack callBack;

    public static interface CallBack{
        void onTidesUpdated();
    }

    private final String LOG_TAG = TidesTask.class.getSimpleName();
    private final Context context;


    public TidesTask(Context context, CallBack callBack) {
        this.context = context;
        this.callBack = callBack;

    }

    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p/>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param params The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected Void doInBackground(String... params) {
        // If there's no zip code, there's nothing to look up.  Verify size of params.
        if (params.length == 0) {
            return null;
        }
        String locationQuery = params[0];

        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String forecastJsonStr = null;

        String format = "json";
        String units = "metric";
        int numDays = 14;

        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are avaiable at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast
            final String FORECAST_BASE_URL =
                    "http://api.sehavniva.no/tideapi.php?";
            final String LAT = "lat";
            final String LNG = "lon";
            final String FROM_TIME = "fromtime";
            final String TO_TIME = "totime";
            final String DATATYPE = "datatype";
            final String REFCODE = "refcode";
            final String PLACE = "place";
            final String FILE = "file";
            final String LANG = "lang";
            final String INTERVAL = "interval";
            final String DST = "dst";
            final String TZONE = "tzone";
            final String TIDE_REUEST = "tide_request";


            Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                    .appendQueryParameter(LAT, params[0])
                    .appendQueryParameter(LNG, params[1])
                    .appendQueryParameter(FROM_TIME, "2015-05-30T00%3A00")
                    .appendQueryParameter(TO_TIME, "2015-05-31T00%3A00")
                    .appendQueryParameter(DATATYPE, "tab")
                    .appendQueryParameter(REFCODE, "cd")
                    .appendQueryParameter(PLACE, "")
                    .appendQueryParameter(FILE, "")
                    .appendQueryParameter(LANG, "nn")
                    .appendQueryParameter(INTERVAL, "60")
                    .appendQueryParameter(DST, "0")
                    .appendQueryParameter(TZONE, "")
                    .appendQueryParameter(TIDE_REUEST, "locationdata")

                    .build();

            URL url = new URL(builtUri.toString());

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }

            System.out.println(buffer);

            XStream xstream = new XStream();
            xstream.alias("tide", Tide.class);
            xstream.alias("locationdata", LocationData.class);
            xstream.alias("location", com.waageweb.tides.app.data.dataModel.Location.class);
            xstream.useAttributeFor(com.waageweb.tides.app.data.dataModel.Location.class, "name");
            xstream.useAttributeFor(com.waageweb.tides.app.data.dataModel.Location.class, "code");
            xstream.useAttributeFor(com.waageweb.tides.app.data.dataModel.Location.class, "latitude");
            xstream.useAttributeFor(com.waageweb.tides.app.data.dataModel.Location.class, "longitude");
            xstream.alias("waterlevel", WaterLevel.class);
            xstream.useAttributeFor(WaterLevel.class, "value");
            xstream.useAttributeFor(WaterLevel.class, "time");
            xstream.useAttributeFor(WaterLevel.class, "flag");

            /*Tide tempTide = (Tide) xstream.fromXML(buffer);


            for (int i = 0; i < tempTide.getLocationdata().getData().size() ; i++){
                    ContentValues cv = new ContentValues();
                    cv.put(TideStrings.Columns.CREATED, new Date().getTime());
                    cv.put(TideStrings.Columns.NAME, String.valueOf(tempTide.getLocationdata().getLocation().getName()));
                    cv.put(TideStrings.Columns.VALUE, String.valueOf(tempTide.getLocationdata().getData().get(i).getValue()));
                    cv.put(TideStrings.Columns.HIGHLOW, String.valueOf(tempTide.getLocationdata().getData().get(i).getFlag()));
                    cv.put(TideStrings.Columns.UNIT, String.valueOf("cm"));
                    context.getContentResolver().insert(TidesProvider.Uris.TIDE_CONTENT_URI, cv);
            }*/

            // Faker data siden kartverket er nede....
            for (int i = 0; i < 4; i++){
                ContentValues cv = new ContentValues();
                cv.put(TideStrings.Columns.CREATED, new Date().getTime());
                cv.put(TideStrings.Columns.NAME, String.valueOf("Bergen"));
                cv.put(TideStrings.Columns.VALUE, String.valueOf(10 * i));
                cv.put(TideStrings.Columns.HIGHLOW, String.valueOf(20*i));
                cv.put(TideStrings.Columns.UNIT, String.valueOf("cm"));
                context.getContentResolver().insert(TidesProvider.Uris.TIDE_CONTENT_URI, cv);
            }

            // Simulate the task using a few seconds
            try {
                Thread.sleep(4000);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }




        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        // This will only happen if there was an error getting or parsing the forecast.
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (callBack != null){
            callBack.onTidesUpdated();
        }
    }
}
