/*
 *  Get Car Information  Async Task retrieves car details from rest api
 *
 */


package com.example.vehicledata;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class GetCarInformation extends AsyncTask<String, Void, JSONArray> {
    private String TAG = GetCarInformation.class.getSimpleName();

    private WeakReference<MainActivity> activityReference;
    // only retain a weak reference to the activity
    GetCarInformation(MainActivity context) {
        activityReference = new WeakReference<>(context);
    }

    @Override
    protected JSONArray doInBackground(String... args) {
        HttpHandler sh = new HttpHandler();
        JSONArray vehicles=null;
        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(args[0]);

        Log.e(TAG, "Response from url: " + jsonStr);

        if (jsonStr != null) {

            try {

                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                vehicles = jsonObj.getJSONArray("lists");

                //vehicles = new JSONArray(jsonStr);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return vehicles;
    }

    @Override
    protected void onPostExecute(JSONArray result) {
        super.onPostExecute(result);
        // get a reference to the activity if it is still there
        MainActivity activity = activityReference.get();
        if (activity == null || activity.isFinishing()) return;
        ArrayList<String> arrayList = new ArrayList<>();
        //int sampleid=10;
        for (int i = 0; i < result.length(); i++) {
            JSONObject c = null;
            try {
                c = result.getJSONObject(i);
                String id = c.getString("vehicle_make");
                arrayList.add(id+(i+1));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        RecyclerView recyclerView=(RecyclerView)activity.findViewById(R.id.m_list_of_vehicles);
        recyclerView.setAdapter(activity.new SimplItemRecyclerViewAdapter(arrayList));
        //Todo: Set different views in vehicle_selection_options and also store information of vehicles
    }

}
