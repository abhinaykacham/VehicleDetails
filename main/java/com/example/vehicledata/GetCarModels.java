package com.example.vehicledata;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.vehicledata.content.VehicleModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
/**
 *  Get Cars Models Async Task retrieves the list of cars models from REST API
 *
 */
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
        ArrayList<VehicleModel> vehicleModelArrayList = new ArrayList<>();
        Integer vehicle_make_id=0;
        for (int i = 0; i < result.length(); i++) {
            JSONObject c = null;
            try {
                c = result.getJSONObject(i);
                String modelName = c.getString("model");
                Integer modelId= c.getInt("id");
                vehicle_make_id = c.getInt("vehicle_make_id");
                VehicleModel vehicleModel = new VehicleModel(modelId,modelName,vehicle_make_id);
                vehicleModelArrayList.add(vehicleModel);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        ArrayAdapter<VehicleModel> arrayAdapter = new ArrayAdapter<>(activity,android.R.layout.simple_spinner_item, vehicleModelArrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        // TODO: Mark 0 as constant before final commit
        spinner.setSelection(0);
        //Todo: obtain make_id and model_id for api call(Use of Wrapper class) and store details
        //TODO: Fix the prefix URL as constant
        new GetCarInformation(activity).execute("https://thawing-beach-68207.herokuapp.com/cars/"+((VehicleModel)spinner.getSelectedItem()).getMakeId()+"/"+((VehicleModel)spinner.getSelectedItem()).getModelId()+"/92603","https://thawing-beach-68207.herokuapp.com/cars/");

    }

}
