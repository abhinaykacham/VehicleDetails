package com.example.vehicledata.content;

public class VehicleDetailInformation {
    String mImageUrl;
    String mVehicleDesc;
    String mPrice;
    String mUpdateDate;

    public VehicleDetailInformation(String imageUrl, String vehicleDesc, String price, String updateDate) {
        mImageUrl = imageUrl;
        mVehicleDesc = vehicleDesc;
        mPrice = price;
        mUpdateDate = updateDate;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public String getVehicleDesc() {
        return mVehicleDesc;
    }

    public void setVehicleDesc(String vehicleDesc) {
        mVehicleDesc = vehicleDesc;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public String getUpdateDate() {
        return mUpdateDate;
    }

    public void setUpdateDate(String updateDate) {
        mUpdateDate = updateDate;
    }

}
