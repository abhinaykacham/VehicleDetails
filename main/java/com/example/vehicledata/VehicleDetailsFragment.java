package com.example.vehicledata;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

// TODO: Customize data based on the data fetched
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VehicleDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VehicleDetailsFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String VEHICLE_ID = "VEHICLE_ID";

    private Integer vehicleID;

    public VehicleDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param vehicleID Parameter 1.
     * @return A new instance of fragment VehicleDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VehicleDetailsFragment newInstance(Integer vehicleID) {
        VehicleDetailsFragment fragment = new VehicleDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(VEHICLE_ID, vehicleID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //TODO Get the data from Main Activity
        }
    }
    //TODO: substitute data of vehicle details with data fetched from MainActivity
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.vehicle_details, container, false);
        return rootView;
    }
}