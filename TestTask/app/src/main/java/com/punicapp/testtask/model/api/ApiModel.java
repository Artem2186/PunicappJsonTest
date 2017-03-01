package com.punicapp.testtask.model.api;

import com.google.gson.annotations.SerializedName;
import com.punicapp.testtask.model.IModelItem;


public class ApiModel implements IModelItem {

    @SerializedName("title")
    private String title;

    @SerializedName("img")
    private String imageUrl;

    private int id;

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
}
