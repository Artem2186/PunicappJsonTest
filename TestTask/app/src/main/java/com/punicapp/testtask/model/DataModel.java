package com.punicapp.testtask.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataModel extends Model {

    @SerializedName("title")
    @Column(name = "title")
    @Expose
    private String title;

    @SerializedName("img")
    @Column(name = "image")
    @Expose
    private String imageUrl;

    @SerializedName("id")
    @Column(name = "serverId")
    @Expose
    private int serverId;

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getServerIdId() {
        return serverId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }
}
