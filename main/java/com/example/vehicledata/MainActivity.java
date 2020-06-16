package com.example.vehicledata;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vehicledata.content.VehicleUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    boolean mTwoPane=false;
    Spinner mModel;
    Spinner mMake;
    private String TAG = MainActivity.class.getSimpleName();
    // URL to get contacts JSON
    private static String url = "https://thawing-beach-68207.herokuapp.com/carmakes";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mModel=findViewById(R.id.m_spinner_model);
        mMake=findViewById(R.id.m_spinner_make);
        new GetCars(this).execute(url);
        //TODO: Below array should be dynamically fetched
        List<String> vehiclesList=new ArrayList<>();
        vehiclesList.add("Ferrari1");
        vehiclesList.add("Ferrari2");
        vehiclesList.add("Ferrari3");
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.m_list_of_vehicles);
        recyclerView.setAdapter(new SimplItemRecyclerViewAdapter(vehiclesList));
        if(findViewById(R.id.vehicle_detail_container)!=null)
            mTwoPane=true;
    }

    //TODO: Below class need to be customised and methods need to be implemented
     class SimplItemRecyclerViewAdapter extends RecyclerView.Adapter
            <SimplItemRecyclerViewAdapter.ViewHolder>{
        List<String> vehiclesList=new ArrayList<>();
        SimplItemRecyclerViewAdapter(List<String> vehiclesList){
            this.vehiclesList=vehiclesList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.make_model_list, viewGroup, false);
            return new ViewHolder(view);
        }

        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
            //holder.mVehicleItem.setText(String.valueOf(position + 1));
            holder.mVehicleItem.setText(vehiclesList.get(position));
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        int selectedSong = holder.getAdapterPosition();
                        VehicleDetailsFragment fragment =
                                VehicleDetailsFragment.newInstance(selectedSong);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.vehicle_detail_container, fragment)
                                .addToBackStack(null)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, VehicleDetailsActivity.class);
                        intent.putExtra("VEHICLE_ID_KEY", holder.getAdapterPosition());
                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return vehiclesList.size();
        }


        class ViewHolder extends  RecyclerView.ViewHolder{
            final View mView;
            final  TextView mVehicleItem;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                mView=itemView;
                mVehicleItem=itemView.findViewById(R.id.id);
            }
        }
    }

}