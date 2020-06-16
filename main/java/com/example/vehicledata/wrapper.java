package com.example.vehicledata;

import com.example.vehicledata.content.VehicleDetailInformation;
import com.example.vehicledata.content.VehicleModel;
import com.example.vehicledata.content.VehicleUtils;

import java.util.ArrayList;
import java.util.List;

public class wrapper {
    static List<VehicleUtils> mVehicleUtilsList=new ArrayList<>();
    static VehicleUtils getsVehicleUtilsById(Integer vehicleId){
        for(VehicleUtils vehicleUtils:mVehicleUtilsList){
            if(vehicleUtils.getVehicleId()==vehicleId)
                return vehicleUtils;
        }
        return null;
    }

    static ArrayList<String> getAllCars(){
        ArrayList<String> cars = new ArrayList<>();
        for(VehicleUtils vehicleUtils:mVehicleUtilsList){
            cars.add(vehicleUtils.getVehicleName());
        }
        return cars;
    }
    static List<VehicleModel> sVehicleModels=new ArrayList<>();
    static ArrayList<String> getAllModels(){
        ArrayList<String> cars = new ArrayList<>();
        for(VehicleModel vehicleModel:sVehicleModels){
            cars.add(vehicleModel.getModelName());
        }
        return cars;
    }
    static List<VehicleDetailInformation> sVehicleDetailInformations=new ArrayList<>();

}
