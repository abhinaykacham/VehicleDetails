package com.example.vehicledata;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.vehicledata.content.VehicleDetailInformation;
import com.example.vehicledata.content.VehicleModel;
import com.example.vehicledata.content.VehicleUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Spinner mModel;
    Spinner mMake;
    List<VehicleDetailInformation> vehiclesList;
    private String TAG = MainActivity.class.getSimpleName();
    VehicleDetailsFragment mFragmentById=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mModel=findViewById(R.id.m_spinner_model);
        mMake=findViewById(R.id.m_spinner_make);
        new GetCars(this).execute(Reference.CAR_MAKE_URL);
        mMake.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                new GetCarModels((MainActivity) parent.getContext())
                        .execute(Reference.CAR_MODEL_URL+((VehicleUtils)parent.getSelectedItem()).getVehicleId().toString());
                mFragmentById = (VehicleDetailsFragment)getSupportFragmentManager().findFragmentById(R.id.vehicle_detail_container);
                if(mFragmentById!=null)
                    getSupportFragmentManager().beginTransaction().remove(mFragmentById).commit();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mMake.setSelection(Reference.SPINNER_INITIAL_POSITION);
            }
        });

        mModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String vehicleInfoURL= Reference.CAR_DETAIL_INFO_URL+((VehicleModel)mModel.getSelectedItem()).getMakeId().toString()
                        +"/"
                        +((VehicleModel)mModel.getSelectedItem()).getModelId().toString()
                        +"/92603";

                new GetCarInformation((MainActivity)parent.getContext())
                    .execute(vehicleInfoURL, Reference.CAR_UPDATED_DETAIL_INFO_URL);
                vehiclesList=new ArrayList<>();
                mFragmentById = (VehicleDetailsFragment)getSupportFragmentManager().findFragmentById(R.id.vehicle_detail_container);
                if(mFragmentById!=null)
                getSupportFragmentManager().beginTransaction().remove(mFragmentById).commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mModel.setSelection(Reference.SPINNER_INITIAL_POSITION);
            }
        });
    }
}