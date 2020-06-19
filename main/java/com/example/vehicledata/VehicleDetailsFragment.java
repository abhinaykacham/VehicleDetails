package com.example.vehicledata;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vehicledata.content.VehicleDetailInformation;
import com.squareup.picasso.Picasso;

// TODO: Customize data based on the data fetched
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VehicleDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VehicleDetailsFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String VEHICLE_ID = "VEHICLE_ID";

    private VehicleDetailInformation mVehicleDetailInformation;
    TextView makeModel;
    TextView price;
    TextView description;
    TextView updatedDate;
    ImageView vehicleImage;
    public VehicleDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param vehicleDetailInformation Parameter 1.
     * @return A new instance of fragment VehicleDetailsFragment.
     */
    public static VehicleDetailsFragment newInstance(VehicleDetailInformation vehicleDetailInformation) {
        VehicleDetailsFragment fragment = new VehicleDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(VEHICLE_ID,vehicleDetailInformation);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mVehicleDetailInformation = (VehicleDetailInformation) getArguments()
                    .getSerializable(VEHICLE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.vehicle_details, container, false);
        if (mVehicleDetailInformation != null) {
            description=rootView.findViewById(R.id.m_txt_vehicle_details_description);
            makeModel=rootView.findViewById(R.id.m_txt_vehicle_details_model);
            price=rootView.findViewById(R.id.m_txt_vehicle_details_price);
            updatedDate=rootView.findViewById(R.id.m_txt_vehicle_details_updated_date);
            vehicleImage= rootView.findViewById(R.id.m_vehicle_details_image);

            description.setText(mVehicleDetailInformation.getVehicleDesc());
            price.setText(mVehicleDetailInformation.getPrice());
            makeModel.setText(mVehicleDetailInformation.getmModel()+ " - "+ mVehicleDetailInformation.getmModel());
            updatedDate.setText("Last update: "+mVehicleDetailInformation.getUpdateDate());
            if(mVehicleDetailInformation.getImageUrl()!=null
                    && mVehicleDetailInformation.getImageUrl().length()!=0) {
                Picasso.get().load(mVehicleDetailInformation.getImageUrl())     //Desired source of Image
                        .placeholder(R.drawable.loading_image)                  //This acts as placeholder until image is fetched
                        .error(R.drawable.image_clip)                           //When Application failed to load image, this image is displayed
                        .into(vehicleImage);                                    //Target View where we would like to place Image
            }else {
                Picasso.get().load(R.drawable.image_clip)                       //Loading Car clip since Source URL is NULL
                        .placeholder(R.drawable.loading_image)                  //This acts as placeholder until image is fetched
                        .into(vehicleImage);
            }
        }
        return rootView;
    }
}