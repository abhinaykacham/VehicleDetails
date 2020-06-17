package com.example.vehicledata;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vehicledata.content.VehicleDetailInformation;
import com.squareup.picasso.Picasso;

/**
 *  A Simple {@link android.app.Activity} subclass
 *  Based on Intent obtained from {@link MainActivity}
 *  we de-serialize object and render data to respective fields.
 */
public class VehicleDetailsActivity extends AppCompatActivity {
    TextView makeModel;
    TextView price;
    TextView description;
    TextView updatedDate;
    ImageView vehicleImage;

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
            vehicleImage= findViewById(R.id.m_vehicle_details_image);

            description.setText(vehicleDetailInformation.getVehicleDesc());
            price.setText(vehicleDetailInformation.getPrice());
            makeModel.setText(vehicleDetailInformation.getmMake()+" - "+vehicleDetailInformation.getmModel());
            updatedDate.setText("Last update: "+vehicleDetailInformation.getUpdateDate());
            Picasso.get().load(vehicleDetailInformation.getImageUrl())  //Desired source of Image
                    .placeholder(R.drawable.loading_image)              //This acts as placeholder until image is fetched
                    .error(R.drawable.image_place_holder)               //When Application failed to load image, this image is displayed
                    .into(vehicleImage);                                //Target where we would like to see our image
        }

    }
}