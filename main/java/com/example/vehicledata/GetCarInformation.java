package com.example.vehicledata;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.vehicledata.content.VehicleDetailInformation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.TimeZone;

/**
 *  Get Car Information  Async Task retrieves car details from REST API
 *
 */
public class GetCarInformation extends AsyncTask<String, Void, ArrayList<VehicleDetailInformation>> {

    private String TAG = GetCarInformation.class.getSimpleName();
    private DateFormat standardDateFormat;
    private DateFormat simpleDateFormat;
    private DecimalFormatSymbols decimalFormatSymbols;
    private Currency currency;
    private HttpHandler httpHandler;
    private ArrayList<VehicleDetailInformation> vehicleDetailInformationArrayList;
    private WeakReference<MainActivity> activityReference;
    private MainActivity activity;
    private DecimalFormat decimalFormat;
    private String JSONResponse;
    private JSONArray updatedInfo;
    private ProgressDialog pDialog;

    // only retain a weak reference to the activity
    GetCarInformation(MainActivity context) {
        activityReference = new WeakReference<>(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress dialog
        pDialog = new ProgressDialog(activityReference.get());
        pDialog.setMessage("Please wait...");
        pDialog.setTitle("Car Details");
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setCancelable(false);
        pDialog.show();

    }

    @Override
    protected ArrayList<VehicleDetailInformation> doInBackground(String... args) {
        httpHandler = new HttpHandler();
        JSONArray vehicleInformationList=null;
        vehicleDetailInformationArrayList = new ArrayList<>();
        standardDateFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
        simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        decimalFormatSymbols=new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator(',');
        currency=Currency.getInstance("USD");
        decimalFormatSymbols.setCurrency(currency);
        decimalFormat=new DecimalFormat(decimalFormatSymbols.getCurrencySymbol()+"###,###,###,###.##",decimalFormatSymbols);
        // Making a request to url and getting response
        JSONResponse = httpHandler.makeServiceCall(args[0]);
        if (JSONResponse != null) {
            try {
                JSONObject jsonObj = new JSONObject(JSONResponse);
                // Getting JSON Array node
                vehicleInformationList = jsonObj.getJSONArray("lists");
            } catch (JSONException e) {
                Log.e(TAG,e.getMessage());
            }
        }
        // get a reference to the activity if it is still there
        activity = activityReference.get();
        if (activity == null || activity.isFinishing())
            return null;
        for (int i = 0; i < vehicleInformationList.length(); i++) {
            JSONObject vehicleObject = null;
            try {
                vehicleObject = vehicleInformationList.getJSONObject(i);
                Integer id = vehicleObject.getInt("id");
                String make = vehicleObject.getString("vehicle_make");
                String model = vehicleObject.getString("model");
                String imageURL = vehicleObject.getString("image_url");
                String veh_desc = vehicleObject.getString("veh_description");
                String price = decimalFormat.format(Double.parseDouble(vehicleObject.getString("price")));
                vehicleDetailInformationArrayList.add(new VehicleDetailInformation(id, make, model, imageURL, veh_desc, price, ""));
            } catch (JSONException e) {
                Log.e(TAG,e.getMessage());
            }

        }
        for(VehicleDetailInformation vehicleDetailInformation:vehicleDetailInformationArrayList) {
            JSONResponse = httpHandler.makeServiceCall(args[1]+vehicleDetailInformation.getmId());
            if (JSONResponse != null) {
                try {
                    updatedInfo = new JSONArray(JSONResponse);
                    standardDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
                    try {
                        JSONObject vehcileJsonObject=updatedInfo.getJSONObject(0);
                        Date updatedDate=standardDateFormat.parse(vehcileJsonObject.getString("updated_at"));
                        vehicleDetailInformation.setUpdateDate(simpleDateFormat.format(updatedDate));
                    } catch (JSONException | ParseException e) {
                        Log.e(TAG,e.getMessage());
                    }
                } catch (JSONException e) {
                    Log.e(TAG,e.getMessage());
                }
            }
        }
        return vehicleDetailInformationArrayList;
    }

    @Override
    protected void onPostExecute(ArrayList<VehicleDetailInformation> vehicleDetailInformationArrayList) {
        pDialog.dismiss();
        super.onPostExecute(vehicleDetailInformationArrayList);

        RecyclerView recyclerView = activity.findViewById(R.id.m_list_of_vehicles);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(activity, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(new SimplItemRecyclerViewAdapter(vehicleDetailInformationArrayList, activityReference));
    }
}
