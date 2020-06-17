package com.example.vehicledata;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.vehicledata.content.VehicleDetailInformation;

/**
 *
 */
//  TODO: Update this class to render layout/activity_vehicle_details.xml which include vehicle_details.xml
public class VehicleDetailsActivity extends AppCompatActivity {
    TextView makeModel;
    TextView price;
    TextView description;
    TextView updatedDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_details);

        if (savedInstanceState == null) {
           VehicleDetailInformation vehicleDetailInformation =
                   (VehicleDetailInformation) getIntent().getSerializableExtra("VEHICLE_INFO");
            description=findViewById(R.id.m_txt_vehicle_details_description);
            makeModel=findViewById(R.id.m_txt_vehicle_details_model);
            price=findViewById(R.id.m_txt_vehicle_details_price);
            updatedDate=findViewById(R.id.m_txt_vehicle_details_updated_date);
            description.setText(vehicleDetailInformation.getVehicleDesc());
            price.setText(vehicleDetailInformation.getPrice());
            makeModel.setText(vehicleDetailInformation.getmMake()+" - "+vehicleDetailInformation.getmModel());
            updatedDate.setText("Last update: "+vehicleDetailInformation.getUpdateDate());
        }

    }
}