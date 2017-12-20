package com.example.team04_final_project.Direction;

import java.util.ArrayList;
import java.util.Map;
import org.w3c.dom.Document;

import com.example.team04_final_project.DirectionActivity;
import com.google.android.gms.maps.model.LatLng;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Thanh Huynh on 2/12/2017.
 */

public class AsyncTaskDrawDirection extends AsyncTask<Map<String, String>, Object, ArrayList<LatLng>> {
    public static final String USER_CURRENT_LAT = "user_current_lat";
    public static final String USER_CURRENT_LONG = "user_current_long";
    public static final String DESTINATION_LAT = "destination_lat";
    public static final String DESTINATION_LONG = "destination_long";
    public static final String DIRECTIONS_MODE = "directions_mode";
    public static String getDuration="" , getDistance="";
    private DirectionActivity activity;
    private Exception exception;
    private ProgressDialog progressDialog;

    public AsyncTaskDrawDirection(DirectionActivity activity) {
        super();
        this.activity = activity;
    }

    public void onPreExecute() {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Calculating directions");
        progressDialog.show();
    }

    @Override
    public void onPostExecute(ArrayList result) {
        progressDialog.dismiss();
        if (exception == null) {
            activity.handleGetDirectionsResult(result);
        } else {
            processException();
        }
    }

    @Override
    protected ArrayList<LatLng> doInBackground(Map<String, String>... params) {
        Map<String, String> paramMap = params[0];
        try {
            LatLng fromPosition = new LatLng(Double.valueOf(paramMap.get(USER_CURRENT_LAT)), Double.valueOf(paramMap.get(USER_CURRENT_LONG)));
            LatLng toPosition = new LatLng(Double.valueOf(paramMap.get(DESTINATION_LAT)), Double.valueOf(paramMap.get(DESTINATION_LONG)));
            GoogleMapsDirection md = new GoogleMapsDirection();
            Document doc = md.getDocument(fromPosition, toPosition, paramMap.get(DIRECTIONS_MODE));
            ArrayList<LatLng> directionPoints = md.getDirection(doc);
            getDuration = md.getDurationText(doc);
            getDistance = md.getDurationText(doc);

            for (int i = 0; i < directionPoints.size(); i++) {
                Log.d("TBT", directionPoints.get(i).toString());
            }
            return directionPoints;
        } catch (Exception e) {
            exception = e;
            return null;
        }
    }

    private void processException() {
        Toast.makeText(activity, "Error retriving data", Toast.LENGTH_LONG).show();
    }
}
