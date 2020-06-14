package com.example.vehicledata.content;

import java.util.HashMap;
import java.util.List;

public class VehicleUtils {
    Integer mVehicleId;
    String mVehicleName;
    HashMap<Integer, List<VehicleModel>> mIdVehicleModelHashMap = new HashMap<Integer, List<VehicleModel>>();
    HashMap<VehicleModel, List<VehicleDetailInformation>> mVehicleDetailInformationHashMap = new HashMap<VehicleModel,List<VehicleDetailInformation>>();

    VehicleUtils(Integer vehicleId,String vehicleName){
        this.mVehicleId = vehicleId;
        this.mVehicleName = vehicleName;
    }

    public Integer getVehicleId() {
        return mVehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        mVehicleId = vehicleId;
    }

    public String getVehicleName() {
        return mVehicleName;
    }

    public void setVehicleName(String vehicleName) {
        mVehicleName = vehicleName;
    }

}
