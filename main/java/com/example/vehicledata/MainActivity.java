package com.example.vehicledata;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.AsyncTasks.GetCarInformation;
import com.example.AsyncTasks.GetCarModels;
import com.example.AsyncTasks.GetCars;
import com.example.Helper.Reference;
import com.example.POJO.VehicleModel;
import com.example.POJO.VehicleUtils;

public class MainActivity extends AppCompatActivity {
    Spinner mModel;
    Spinner mMake;
    private String TAG = MainActivity.class.getSimpleName();
    VehicleDetailsFragment mFragmentById=null;
    String MAKE_POSITION="MAKE_POSITION";
    String MODEL_POSITION="MODEL_POSITION";
    Integer makePosition=0;
    Integer modelPosition=0;
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        makePosition=mMake.getSelectedItemPosition();
        modelPosition=mModel.getSelectedItemPosition();
        outState.putInt(MAKE_POSITION,makePosition);
        outState.putInt(MODEL_POSITION,modelPosition);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState!=null){
            makePosition=savedInstanceState.getInt(MAKE_POSITION);
            modelPosition=savedInstanceState.getInt(MODEL_POSITION);
        }

        mModel=findViewById(R.id.m_spinner_model);
        mMake=findViewById(R.id.m_spinner_make);
        new GetCars(this,makePosition,modelPosition).execute(Reference.CAR_MAKE_URL);
        mMake.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                new GetCarModels((MainActivity) parent.getContext(),modelPosition)
                        .execute(Reference.CAR_MODEL_URL+((VehicleUtils)parent.getSelectedItem()).getVehicleId().toString());
                mFragmentById = (VehicleDetailsFragment)getSupportFragmentManager().findFragmentById(R.id.vehicle_detail_container);
                if(mFragmentById!=null)
                    getSupportFragmentManager().beginTransaction().remove(mFragmentById).commit();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
                mFragmentById = (VehicleDetailsFragment)getSupportFragmentManager().findFragmentById(R.id.vehicle_detail_container);
                if(mFragmentById!=null)
                getSupportFragmentManager().beginTransaction().remove(mFragmentById).commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        modelPosition=0;
    }
}