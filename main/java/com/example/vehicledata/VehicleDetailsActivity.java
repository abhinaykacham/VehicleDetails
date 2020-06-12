package com.example.vehicledata;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//  TODO: Update this class to render layout/activity_vehicle_details.xml which include vehicle_details.xml
//  TODO: Update  this table to render data fethced from MainActivity either through Intent
//          or by creating new REST API call with ID
public class VehicleDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_details);
    }
}