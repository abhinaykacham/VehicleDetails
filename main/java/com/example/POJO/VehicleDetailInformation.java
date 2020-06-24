package com.example.POJO;

import java.io.Serializable;

/**
 * This class contains details of each vehicle associated with
 * a make and a model
 */
public class VehicleDetailInformation implements Serializable {
    Integer mId;
    String mMake;
    String mModel;
    String mImageUrl;
    String mVehicleDesc;
    String mPrice;
    String mUpdateDate;
    Integer mMakeId;
    Integer mModelId;

    public VehicleDetailInformation(Integer id,String make,String model, String imageUrl, String vehicleDesc, String price, String updateDate,Integer makeId,Integer modelId) {
        mId=id;
        mMake=make;
        mModel=model;
        mImageUrl = imageUrl;
        mVehicleDesc = vehicleDesc;
        mPrice = price;
        mUpdateDate = updateDate;
        mMakeId = makeId;
        mModelId = modelId;
    }

    public Integer getMakeId() {
        return mMakeId;
    }

    public void setMakeId(Integer makeId) {
        mMakeId = makeId;
    }

    public Integer getModelId() {
        return mModelId;
    }

    public void setModelId(Integer modelId) {
        mModelId = modelId;
    }


    public Integer getmId() {
        return mId;
    }

    public void setmId(Integer mId) {
        this.mId = mId;
    }

    public String getmMake() {
        return mMake;
    }

    public void setmMake(String mMake) {
        this.mMake = mMake;
    }

    public String getmModel() {
        return mModel;
    }

    public void setmModel(String mModel) {
        this.mModel = mModel;
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
