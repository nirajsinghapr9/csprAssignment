package com.example.csparmaps.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class APiResponse implements Serializable {

    @SerializedName("success")
    @Expose
    private List<Success> success = null;
    @SerializedName("location")
    @Expose
    private List<Location> location = null;

    public List<Success> getSuccess() {
        return success;
    }

    public void setSuccess(List<Success> success) {
        this.success = success;
    }

    public List<Location> getLocation() {
        return location;
    }

    public void setLocation(List<Location> location) {
        this.location = location;
    }

}
