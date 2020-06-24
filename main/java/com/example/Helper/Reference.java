package com.example.Helper;

import com.example.POJO.VehicleDetailInformation;

public class Reference {
    /**
     * To give Spinner a default state below Integer is used.
     */
    public static final Integer SPINNER_INITIAL_POSITION=0;
    /**
     * URL to fetch Car makers
     */
    public static final String CAR_MAKE_URL="https://thawing-beach-68207.herokuapp.com/carmakes";
    /**
     * URL to fetch Models of a particular maker whose ID concatenated at the end.
     */
    public static final String CAR_MODEL_URL="https://thawing-beach-68207.herokuapp.com/carmodelmakes/";
    /**
     * URL to fetch details of a particular car by replacing <model>,<make> with respective values.
     */
    public static final String CAR_DETAIL_INFO_URL="https://thawing-beach-68207.herokuapp.com/cars/";
    /**
     * URL to fetch updated details of a car by concatenating Unique Car ID at the end of below URL
     */
    public static final String CAR_UPDATED_DETAIL_INFO_URL="https://thawing-beach-68207.herokuapp.com/cars/";

    public static VehicleDetailInformation sVehicleDetailInformation;
}
