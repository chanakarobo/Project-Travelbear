package com.example.travelbear;

public class DataModel {

    private int location_id;
    private String comment;

    public DataModel(int location_id, String comment) {
        this.location_id = location_id;
        this.comment = comment;
    }

    public int getLocation_id() {
        return location_id;
    }

    public String getComment() {
        return comment;
    }
}
