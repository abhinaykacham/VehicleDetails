package com.example.vehicledata;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
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
    String SAVED_OBJECT="savedObject";
    SpannableStringBuilder vehicleDescriptionFormatter;
    String vehicleDescriptionLabel;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(SAVED_OBJECT, getIntent().getSerializableExtra("VEHICLE_INFO"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_details);
        description=findViewById(R.id.m_txt_vehicle_details_description);
        makeModel=findViewById(R.id.m_txt_vehicle_details_model);
        price=findViewById(R.id.m_txt_vehicle_details_price);
        updatedDate=findViewById(R.id.m_txt_vehicle_details_updated_date);
        vehicleImage= findViewById(R.id.m_vehicle_details_image);
        VehicleDetailInformation vehicleDetailInformation;
        if (savedInstanceState == null) {
            vehicleDetailInformation =
                    (VehicleDetailInformation) getIntent().getSerializableExtra("VEHICLE_INFO");
        }
        else{
            vehicleDetailInformation=(VehicleDetailInformation)savedInstanceState.getSerializable(SAVED_OBJECT);
        }

        vehicleDescriptionLabel=getString(R.string.vehicle_description_label);
        vehicleDescriptionFormatter = new SpannableStringBuilder(vehicleDescriptionLabel+vehicleDetailInformation.getVehicleDesc());
        vehicleDescriptionFormatter.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD),
                0, vehicleDescriptionLabel.length() , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        description.setText(vehicleDescriptionFormatter);

        price.setText(vehicleDetailInformation.getPrice());
        makeModel.setText(vehicleDetailInformation.getmMake()+" - "+vehicleDetailInformation.getmModel());
        updatedDate.setText("Last update: "+vehicleDetailInformation.getUpdateDate());

        if(vehicleDetailInformation.getImageUrl()!=null
                && vehicleDetailInformation.getImageUrl().length()!=0) {
            Picasso.get().load(vehicleDetailInformation.getImageUrl())    //Desired source of Image
                    .placeholder(R.drawable.loading_image)                  //This acts as placeholder until image is fetched
                    .error(R.drawable.image_clip)                           //When Application failed to load image, this image is displayed
                    .into(vehicleImage);
        }else {
            Picasso.get().load(R.drawable.image_clip)                   //Loading Car clip since Source URL is NULL
                    .placeholder(R.drawable.loading_image)                  //This acts as placeholder until image is fetched
                    .into(vehicleImage);
        }
    }
}