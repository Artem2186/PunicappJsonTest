package com.punicapp.testtask.model;

public interface IModelItem extends IDao {
    String getTitle();

    String getImageUrl();

    int getId();

    void setTitle(String title);

    void setImageUrl(String imageUrl);

    void setId(int id);
}
