package com.example.POJO;

/**
 * This Class represents data of a maker
 */
public class VehicleUtils {
    Integer mVehicleId;
    String mVehicleName;

    public VehicleUtils(Integer vehicleId, String vehicleName){
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

    @Override
    public String toString() {
        return mVehicleName;
    }
}
