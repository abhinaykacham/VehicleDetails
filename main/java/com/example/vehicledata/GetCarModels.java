/*
 *  Get Cars Models Async Task retrieves the list of cars models from rest api
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

public class GetCarModels extends AsyncTask<String, Void, JSONArray> {
    private String TAG = GetCarModels.class.getSimpleName();

    private WeakReference<MainActivity> activityReference;
    // only retain a weak reference to the activity
    GetCarModels(MainActivity context) {
        activityReference = new WeakReference<>(context);
    }

    @Override
    protected JSONArray doInBackground(String... args) {
        HttpHandler sh = new HttpHandler();
        JSONArray models=null;
        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(args[0]);

        Log.e(TAG, "Response from url: " + jsonStr);

        if (jsonStr != null) {

            try {

                models = new JSONArray(jsonStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return models;
    }

    @Override
    protected void onPostExecute(JSONArray result) {
        super.onPostExecute(result);
        // get a reference to the activity if it is still there
        MainActivity activity = activityReference.get();
        if (activity == null || activity.isFinishing()) return;
        Spinner spinner = activity.findViewById(R.id.m_spinner_model);
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < result.length(); i++) {
            JSONObject c = null;
            try {
                c = result.getJSONObject(i);
                String id = c.getString("model");
                arrayList.add(id);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(activity,android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        //Todo: obtain make_id and model_id for api call(Use of Wrapper class) and store details
        new GetCarInformation(activity).execute("https://thawing-beach-68207.herokuapp.com/cars/10/20/92603");
    }

}
