/*
 *  Get Cars Async Task retrieves the list of cars details from rest api
 *
 */

package com.example.vehicledata;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class GetCars extends AsyncTask<String, Void, JSONArray> {
    private String TAG = GetCars.class.getSimpleName();

    private WeakReference<MainActivity> activityReference;
    // only retain a weak reference to the activity
    GetCars(MainActivity context) {
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

                vehicles = new JSONArray(jsonStr);

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
        Spinner spinner = activity.findViewById(R.id.m_spinner_make);
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < result.length(); i++) {
            JSONObject c = null;
            try {
                c = result.getJSONObject(i);
                String id = c.getString("vehicle_make");
                arrayList.add(id);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(activity,android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        //Todo: store details and make rest call dynamic
        new GetCarModels(activity).execute("https://thawing-beach-68207.herokuapp.com/carmodelmakes/3");
    }

}
