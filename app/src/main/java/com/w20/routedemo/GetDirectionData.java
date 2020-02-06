package com.w20.routedemo;

import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;

public class GetDirectionData extends AsyncTask<Object, String, String> {

    String directionData;
    GoogleMap mMap;
    String url;

    String distance, duration;

    LatLng latlng;

    @Override
    protected String doInBackground(Object... objects) {
        mMap = (GoogleMap)objects[0];
        url = (String) objects[1];
        latlng = (LatLng) objects[2];

        FetchUrl fetchUrl = new FetchUrl();
        try {
            directionData = fetchUrl.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return directionData;
    }

    @Override
    protected void onPostExecute(String s) {
        HashMap<String, String> distanceData = null;
        DataParser distanceParser = new DataParser();
        distanceData = distanceParser.parseDistance(s);

        distance = distanceData.get("distance");
        duration = distanceData.get("duration");

        mMap.clear();
        // Create New Marker with new Title And Snippet
        MarkerOptions options = new MarkerOptions().position(latlng).draggable(true).title("Duration: " + duration).snippet("Distance: " + distance);
        mMap.addMarker(options);
    }
}
