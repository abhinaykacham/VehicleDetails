package com.example.vehicledata;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    boolean twoPane;
    Spinner mModel;
    Spinner mMake;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(findViewById(R.id.vehicle_detail_container)!=null)
            twoPane=true;
        mModel=findViewById(R.id.m_spinner_model);
        mMake=findViewById(R.id.m_spinner_make);
        //TODO: Below array should be dynamically fetched
        List<String> vehiclesList=new ArrayList<>();
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.m_list_of_vehicles);
        recyclerView.setAdapter(new SimplItemRecyclerViewAdapter(vehiclesList));
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
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return null;
        }

        public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

        }

        @Override
        public int getItemCount() {
            return vehiclesList.size();
        }


        class ViewHolder extends  RecyclerView.ViewHolder{
            final View mView;
            TextView mVehicleItem;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                mView=itemView;
                mVehicleItem=findViewById(R.id.id);
            }
        }
    }

}