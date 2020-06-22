package com.example.AsyncTasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.Helper.HttpHandler;
import com.example.vehicledata.MainActivity;
import com.example.vehicledata.R;
import com.example.Helper.Reference;
import com.example.POJO.VehicleUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 *  Get Cars Async Task retrieves the list of cars details from REST API
 *
 */
public class GetCars extends AsyncTask<String, Void, JSONArray> {
    private String TAG = GetCars.class.getSimpleName();
    private ProgressDialog pDialog;
    int makePosition;
    int modelPosition;
    private WeakReference<MainActivity> activityReference;
    // only retain a weak reference to the activity
    public GetCars(MainActivity context, int makePosition, int modelPosition) {
        activityReference = new WeakReference<>(context);
        this.makePosition=makePosition;
        this.modelPosition=modelPosition;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress dialog
        pDialog = new ProgressDialog(activityReference.get());
        pDialog.setMessage("Please wait...");
        pDialog.setTitle("Fetching list of Car Makers");
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setCancelable(false);
        pDialog.show();

    }

    @Override
    protected JSONArray doInBackground(String... args) {
        HttpHandler httpHandler = new HttpHandler();
        JSONArray vehicles=null;
        // Making a request to url and getting response
        String JSONResponse = httpHandler.makeServiceCall(args[0]);

        if (JSONResponse != null) {
            try {
                vehicles = new JSONArray(JSONResponse);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return vehicles;
    }

    @Override
    protected void onPostExecute(JSONArray result) {
        super.onPostExecute(result);
        pDialog.dismiss();
        // get a reference to the activity if it is still there
        MainActivity activity = activityReference.get();
        if (activity == null || activity.isFinishing()) return;
        Spinner spinner = activity.findViewById(R.id.m_spinner_make);
        ArrayList<VehicleUtils> vehicleUtilsArrayList = new ArrayList<>();
        for (int i = 0; i < result.length(); i++) {
            JSONObject makerJSONObject = null;
            try {
                makerJSONObject = result.getJSONObject(i);
                String vehicleName = makerJSONObject.getString("vehicle_make");
                Integer vehicleId = makerJSONObject.getInt("id");
                VehicleUtils vehicleUtils = new VehicleUtils(vehicleId,vehicleName);
                vehicleUtilsArrayList.add(vehicleUtils);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        ArrayAdapter<VehicleUtils> arrayAdapter = new ArrayAdapter<VehicleUtils>(activity,android.R.layout.simple_spinner_item,vehicleUtilsArrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(makePosition);
        new GetCarModels(activity,modelPosition).execute(Reference.CAR_MODEL_URL+((VehicleUtils)spinner.getSelectedItem()).getVehicleId());
    }

}
