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
    boolean mTwoPane=false;
    Spinner mModel;
    Spinner mMake;
    List<VehicleDetailInformation> vehiclesList;
    private String TAG = MainActivity.class.getSimpleName();
    // URL to get contacts JSON
    private static String fetchCarMakers = "https://thawing-beach-68207.herokuapp.com/carmakes";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mModel=findViewById(R.id.m_spinner_model);
        mMake=findViewById(R.id.m_spinner_make);
        new GetCars(this).execute(fetchCarMakers);
        mMake.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                new GetCarModels((MainActivity) parent.getContext())
                        .execute("https://thawing-beach-68207.herokuapp.com/carmodelmakes/"+((VehicleUtils)parent.getSelectedItem()).getVehicleId().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mMake.setSelection(0);
            }
        });

        mModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                new GetCarInformation((MainActivity)parent.getContext())
                    .execute("https://thawing-beach-68207.herokuapp.com/cars/"+((VehicleModel)mModel.getSelectedItem()).getMakeId()+"/"+((VehicleModel)mModel.getSelectedItem()).getModelId()+"/92603");
                vehiclesList=new ArrayList<>();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mModel.setSelection(0);
            }
        });
    }
}

