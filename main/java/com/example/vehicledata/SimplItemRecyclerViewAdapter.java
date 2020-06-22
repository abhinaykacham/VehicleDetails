package com.example.vehicledata;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.POJO.VehicleDetailInformation;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * This class used to render and provide actions for list of vehicles obtained by selecting make and model
 */
public class SimplItemRecyclerViewAdapter extends RecyclerView.Adapter<SimplItemRecyclerViewAdapter.ViewHolder>{

    List<VehicleDetailInformation> vehiclesList;
    private WeakReference<MainActivity> reference;
    MainActivity activityReference;
    Boolean mTwoPane=false;
    public SimplItemRecyclerViewAdapter(List<VehicleDetailInformation> vehiclesList, WeakReference<MainActivity> context) {
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
        //Displaying Vehicle model and Unique ID in the list
        holder.mVehicleItem.setText(vehiclesList.get(position).getmId().toString());
        holder.mModelName.setText(vehiclesList.get(position).getmModel());
        if(vehiclesList.get(position).getImageUrl()!=null
                && vehiclesList.get(position).getImageUrl().length()!=0) {
            Picasso.get().load(vehiclesList.get(position).getImageUrl())    //Desired source of Image
                    .placeholder(R.drawable.loading_image)                  //This acts as placeholder until image is fetched
                    .error(R.drawable.image_clip)                           //When Application failed to load image, this image is displayed
                    .into(holder.mImageView);
        }else {
            Picasso.get().load(R.drawable.image_clip)                   //Loading Car clip since Source URL is NULL
                    .placeholder(R.drawable.loading_image)                  //This acts as placeholder until image is fetched
                    .into(holder.mImageView);
        }
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
        final ImageView mImageView;
        final TextView  mModelName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
            mVehicleItem=itemView.findViewById(R.id.id);
            mModelName = itemView.findViewById(R.id.modelName);
            mImageView = itemView.findViewById(R.id.imgTvshow);
        }
    }
}