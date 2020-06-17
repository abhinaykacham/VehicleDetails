package com.example.vehicledata;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vehicledata.content.VehicleDetailInformation;

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
    // TODO: Rename and change types and number of parameters
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
            //TODO Get the data from Main Activity
            mVehicleDetailInformation = (VehicleDetailInformation) getArguments()
                    .getSerializable(VEHICLE_ID);
        }
    }
    //TODO: substitute data of vehicle details with data fetched from MainActivity
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
            description.setText(mVehicleDetailInformation.getVehicleDesc());
            price.setText(mVehicleDetailInformation.getPrice());
            makeModel.setText(mVehicleDetailInformation.getmModel()+ " - "+ mVehicleDetailInformation.getmModel());
            updatedDate.setText("Last update: "+mVehicleDetailInformation.getUpdateDate());

        }
        return rootView;
    }
}