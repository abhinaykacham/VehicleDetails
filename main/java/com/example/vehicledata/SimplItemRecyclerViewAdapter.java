package com.example.vehicledata;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vehicledata.content.VehicleDetailInformation;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

class SimplItemRecyclerViewAdapter extends RecyclerView.Adapter<SimplItemRecyclerViewAdapter.ViewHolder>{

    List<VehicleDetailInformation> vehiclesList=new ArrayList<>();
    private WeakReference<MainActivity> reference;
    MainActivity activityReference;
    Boolean mTwoPane=false;
    SimplItemRecyclerViewAdapter(List<VehicleDetailInformation> vehiclesList, WeakReference<MainActivity> context) {
        this.vehiclesList=vehiclesList;
        this.reference = context;
        }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.make_model_list, viewGroup, false);
        activityReference=reference.get();
        if(activityReference.findViewById(R.id.vehicle_detail_container)!=null)
            mTwoPane=true;
        return new ViewHolder(view);
    }

    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        //holder.mVehicleItem.setText(String.valueOf(position + 1));
        holder.mVehicleItem.setText(vehiclesList.get(position).getmModel()+"      "+vehiclesList.get(position).getmId());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTwoPane) {
                    VehicleDetailInformation vehicleDetailInformation = vehiclesList.get(holder.getAdapterPosition());
                    VehicleDetailsFragment fragment =
                            VehicleDetailsFragment.newInstance(vehicleDetailInformation);
                    activityReference.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.vehicle_detail_container, fragment)
                            .addToBackStack(null)
                            .commit();
                } else {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, VehicleDetailsActivity.class);
                    intent.putExtra("VEHICLE_INFO", vehiclesList.get(holder.getAdapterPosition()));
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
        final TextView mVehicleItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
            mVehicleItem=itemView.findViewById(R.id.id);
        }
    }
}