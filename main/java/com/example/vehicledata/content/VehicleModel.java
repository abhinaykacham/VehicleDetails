package com.example.vehicledata.content;

public class VehicleModel {
    Integer mModelId;
    String mModelName;
    Integer mMakeId;

    public VehicleModel(Integer modelId, String modelName, Integer makeId) {
        mModelId = modelId;
        mModelName = modelName;
        mMakeId = makeId;
    }

    public void setModelId(Integer modelId) {
        mModelId = modelId;
    }

    public void setModelName(String modelName) {
        mModelName = modelName;
    }

    public void setMakeId(Integer makeId) {
        mMakeId = makeId;
    }

    public Integer getMakeId() {
        return mMakeId;
    }

    public Integer getModelId() {
        return mModelId;
    }

    public String getModelName() {
        return mModelName;
    }




}
