package com.punicapp.testtask.api.retrofit;

import com.punicapp.testtask.model.DataModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IRetrofitApi {

    @GET("/androids")
    Call<List<DataModel>> loadApiData();
}
