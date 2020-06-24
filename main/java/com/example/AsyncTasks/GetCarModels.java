package com.example.AsyncTasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.Helper.HttpHandler;
import com.example.vehicledata.MainActivity;
import com.example.vehicledata.R;
import com.example.Helper.Reference;
import com.example.POJO.VehicleModel;

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
    private ProgressDialog pDialog;
    private WeakReference<MainActivity> activityReference;
    int modelPosition;
    // only retain a weak reference to the activity
    public GetCarModels(MainActivity context, int modelPosition) {
        activityReference = new WeakReference<>(context);
        this.modelPosition=modelPosition;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress dialog
        pDialog = new ProgressDialog(activityReference.get());
        pDialog.setMessage("Please wait...");
        pDialog.setTitle("Fetching list of Car Models");
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setCancelable(false);
        pDialog.show();

    }


    @Override
    protected JSONArray doInBackground(String... args) {
        HttpHandler sh = new HttpHandler();
        JSONArray vehicleJSONModelList=null;
        // Making a request to url and getting response
        String JSONResponse = sh.makeServiceCall(args[0]);

        if (JSONResponse != null) {
            try {
                vehicleJSONModelList = new JSONArray(JSONResponse);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return vehicleJSONModelList;
    }

    @Override
    protected void onPostExecute(JSONArray result) {
        super.onPostExecute(result);
        pDialog.dismiss();
        // get a reference to the activity if it is still there
        MainActivity activity = activityReference.get();
        if (activity == null || activity.isFinishing()) return;
        Spinner spinner = activity.findViewById(R.id.m_spinner_model);
        ArrayList<VehicleModel> vehicleModelArrayList = new ArrayList<>();
        for (int i = 0; i < result.length(); i++) {
            JSONObject c = null;
            try {
                c = result.getJSONObject(i);
                String modelName = c.getString("model");
                Integer modelId= c.getInt("id");
                Integer vehicle_make_id = c.getInt("vehicle_make_id");
                VehicleModel vehicleModel = new VehicleModel(modelId,modelName,vehicle_make_id);
                vehicleModelArrayList.add(vehicleModel);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        ArrayAdapter<VehicleModel> arrayAdapter = new ArrayAdapter<>(activity,android.R.layout.simple_spinner_dropdown_item, vehicleModelArrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(modelPosition);

        String makeId = ((VehicleModel)spinner.getSelectedItem()).getMakeId().toString();
        String modelId = ((VehicleModel)spinner.getSelectedItem()).getModelId().toString();
        String vehicleInfoURL= Reference.CAR_DETAIL_INFO_URL+((VehicleModel)spinner.getSelectedItem()).getMakeId().toString()
                +"/"
                +((VehicleModel)spinner.getSelectedItem()).getModelId().toString()
                +"/92603";

        new GetCarInformation(activity).execute(vehicleInfoURL,Reference.CAR_UPDATED_DETAIL_INFO_URL,makeId,modelId);

    }

}
