package com.example.vehicledata;

import android.os.AsyncTask;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.vehicledata.content.VehicleDetailInformation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
/**
 *  Get Car Information  Async Task retrieves car details from REST API
 *
 */
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
        ArrayList<VehicleDetailInformation> vehicleDetailInformationArrayList = new ArrayList<>();
        //int sampleid=10;
        for (int i = 0; i < result.length(); i++) {
            JSONObject c = null;
            try {
                c = result.getJSONObject(i);
                Integer id=c.getInt("id");
                String make=c.getString("vehicle_make");
                String model=c.getString("model");
                String imageURL=c.getString("image_url");
                String veh_desc=c.getString("veh_description");
                String price=c.getString("price");
                vehicleDetailInformationArrayList.add(new VehicleDetailInformation(id,make,model,imageURL,veh_desc,price,""));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        RecyclerView recyclerView=(RecyclerView)activity.findViewById(R.id.m_list_of_vehicles);
        recyclerView.setItemAnimator( new DefaultItemAnimator());
        recyclerView.setAdapter(new SimplItemRecyclerViewAdapter(vehicleDetailInformationArrayList,activityReference));
    }

}
