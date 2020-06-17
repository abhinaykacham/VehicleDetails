package com.example.vehicledata;

import android.os.AsyncTask;
import android.support.v7.widget.DefaultItemAnimator;
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

    private WeakReference<MainActivity> activityReference;
    MainActivity activity;
    // only retain a weak reference to the activity
    GetCarInformation(MainActivity context) {
        activityReference = new WeakReference<>(context);
    }

    @Override
    protected ArrayList<VehicleDetailInformation> doInBackground(String... args) {
        HttpHandler sh = new HttpHandler();
        JSONArray vehicles=null;
        ArrayList<VehicleDetailInformation> vehicleDetailInformationArrayList = new ArrayList<>();
        DateFormat standardDateFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
        DateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        DecimalFormatSymbols decimalFormatSymbols=new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator(',');
        Currency currency=Currency.getInstance("USD");
        decimalFormatSymbols.setCurrency(currency);
        DecimalFormat decimalFormat=new DecimalFormat(decimalFormatSymbols.getCurrencySymbol()+"###,###,###,###.##",decimalFormatSymbols);
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
        // get a reference to the activity if it is still there
        activity = activityReference.get();
        if (activity == null || activity.isFinishing())
            return null;
        for (int i = 0; i < vehicles.length(); i++) {
            JSONObject c = null;
            try {
                c = vehicles.getJSONObject(i);
                Integer id = c.getInt("id");
                String make = c.getString("vehicle_make");
                String model = c.getString("model");
                String imageURL = c.getString("image_url");
                String veh_desc = c.getString("veh_description");
                String price = decimalFormat.format(Double.parseDouble(c.getString("price")));
                vehicleDetailInformationArrayList.add(new VehicleDetailInformation(id, make, model, imageURL, veh_desc, price, ""));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        for(VehicleDetailInformation vehicleDetailInformation:vehicleDetailInformationArrayList) {
            jsonStr = sh.makeServiceCall(args[1]+vehicleDetailInformation.getmId());
            JSONArray updatedInfo = null;

            if (jsonStr != null) {
                try {
                    updatedInfo = new JSONArray(jsonStr);
                    standardDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
                    try {
                        JSONObject vehcileJsonObject=updatedInfo.getJSONObject(0);
                        Date updatedDate=standardDateFormat.parse(vehcileJsonObject.getString("updated_at"));
                        vehicleDetailInformation.setUpdateDate(simpleDateFormat.format(updatedDate));
                    } catch (JSONException | ParseException e) {
                        e.printStackTrace();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return vehicleDetailInformationArrayList;
    }

    @Override
    protected void onPostExecute(ArrayList<VehicleDetailInformation> vehicleDetailInformationArrayList) {
        super.onPostExecute(vehicleDetailInformationArrayList);

        RecyclerView recyclerView = activity.findViewById(R.id.m_list_of_vehicles);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(activity, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(new SimplItemRecyclerViewAdapter(vehicleDetailInformationArrayList, activityReference));
    }

}
