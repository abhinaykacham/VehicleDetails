package com.example.vehicledata;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vehicledata.content.VehicleDetailInformation;
import com.squareup.picasso.Picasso;

/**
 * A Simple {@link android.app.Activity} subclass
 * Based on Intent obtained from {@link MainActivity}
 * we de-serialize object and render data to respective fields.
 */
public class VehicleDetailsActivity extends AppCompatActivity {
    TextView makeModel;
    TextView price;
    TextView description;
    TextView updatedDate;
    ImageView vehicleImage;
    String SAVED_OBJECT = "savedObject";
    SpannableString vehicleFormatter;
    String vehicleDescriptionLabel;
    String vehicleLastUpdateLabel;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(SAVED_OBJECT, getIntent().getSerializableExtra("VEHICLE_INFO"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        description = findViewById(R.id.m_txt_vehicle_details_description);
        makeModel = findViewById(R.id.m_txt_vehicle_details_model);
        price = findViewById(R.id.m_txt_vehicle_details_price);
        updatedDate = findViewById(R.id.m_txt_vehicle_details_updated_date);
        vehicleImage = findViewById(R.id.m_vehicle_details_image);
        VehicleDetailInformation vehicleDetailInformation;
        if (savedInstanceState == null) {
            vehicleDetailInformation =
                    (VehicleDetailInformation) getIntent().getSerializableExtra("VEHICLE_INFO");
        } else {
            vehicleDetailInformation = (VehicleDetailInformation) savedInstanceState.getSerializable(SAVED_OBJECT);
        }

        vehicleDescriptionLabel = getString(R.string.vehicle_description_label);
        vehicleFormatter = new SpannableString(vehicleDescriptionLabel + vehicleDetailInformation.getVehicleDesc());
        vehicleFormatter.setSpan(new RelativeSizeSpan(1.6f), 0, 21, 0); // set size
        vehicleFormatter.setSpan(new ForegroundColorSpan(Color.parseColor("#008b8b")), 0, 21, 0);// set color
        description.setText(vehicleFormatter);

        price.setText(vehicleDetailInformation.getPrice());
        makeModel.setText(vehicleDetailInformation.getmMake() + " - " + vehicleDetailInformation.getmModel());
        vehicleLastUpdateLabel = getString(R.string.vehicle_updatedate_label);
        vehicleFormatter = new SpannableString(vehicleLastUpdateLabel + vehicleDetailInformation.getUpdateDate());
        vehicleFormatter.setSpan(new RelativeSizeSpan(1f), 0, 12, 0); // set size
        vehicleFormatter.setSpan(new ForegroundColorSpan(Color.parseColor("#008b8b")), 0, 12, 0);// set color
        updatedDate.setText(vehicleFormatter);

        if (vehicleDetailInformation.getImageUrl() != null
                && vehicleDetailInformation.getImageUrl().length() != 0) {
            Picasso.get().load(vehicleDetailInformation.getImageUrl())    //Desired source of Image
                    .placeholder(R.drawable.loading_image)                  //This acts as placeholder until image is fetched
                    .error(R.drawable.image_clip)                           //When Application failed to load image, this image is displayed
                    .into(vehicleImage);
        } else {
            Picasso.get().load(R.drawable.image_clip)                   //Loading Car clip since Source URL is NULL
                    .placeholder(R.drawable.loading_image)                  //This acts as placeholder until image is fetched
                    .into(vehicleImage);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}